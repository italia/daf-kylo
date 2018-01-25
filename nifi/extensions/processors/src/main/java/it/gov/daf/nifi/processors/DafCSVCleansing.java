package it.gov.daf.nifi.processors;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.logging.ComponentLog;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.util.StopWatch;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@EventDriven
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"csv", "daf"})
@CapabilityDescription("Operate some CSV cleansing before ingestion")
public class DafCSVCleansing extends AbstractProcessor {

    // Relationships
    public static final Relationship REL_SUCCESS = new Relationship.Builder()
        .name("success")
        .description("Successful result.")
        .build();

    public static final Relationship REL_FAILURE = new Relationship.Builder()
        .name("failure")
        .description("Error in the CSV file")
        .build();

    public static final PropertyDescriptor SEPARATOR_CHAR = new PropertyDescriptor.Builder()
        .name("Separator Char")
        .description("The CSV Separator Char")
        .required(true)
        .defaultValue("local")
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .expressionLanguageSupported(true)
        .build();

    public static final PropertyDescriptor QUOTE_CHAR = new PropertyDescriptor.Builder()
        .name("Quote Char")
        .description("The CSV Quote Char")
        .required(true)
        .defaultValue("local")
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .expressionLanguageSupported(true)
        .build();

    public static final PropertyDescriptor ESCAPE_CHAR = new PropertyDescriptor.Builder()
        .name("Escape Char")
        .description("The CSV Escape Char")
        .required(true)
        .defaultValue("local")
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .expressionLanguageSupported(true)
        .build();

    private final Set<Relationship> relationships;

    /**
     * List of properties
     */
    private List<PropertyDescriptor> propDescriptors;

    public DafCSVCleansing() {
        final Set<Relationship> r = new HashSet<>();
        r.add(REL_SUCCESS);
        r.add(REL_FAILURE);
        relationships = Collections.unmodifiableSet(r);
    }

    @Override
    protected void init(ProcessorInitializationContext context) {
        super.init(context);

        // Create list of properties
        final List<PropertyDescriptor> pds = new ArrayList<>();
        pds.add(SEPARATOR_CHAR);
        pds.add(QUOTE_CHAR);
        pds.add(ESCAPE_CHAR);
        propDescriptors = Collections.unmodifiableList(pds);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return propDescriptors;
    }

    @Override
    public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {
        ComponentLog logger = getLogger();

        FlowFile flowFile = session.get();
        if (flowFile == null) {
            return;
        }

        String separatorChar = context.getProperty(SEPARATOR_CHAR).evaluateAttributeExpressions(flowFile).getValue().trim();
        String quoteChar = context.getProperty(QUOTE_CHAR).evaluateAttributeExpressions(flowFile).getValue().trim();
        String escapeChar = context.getProperty(ESCAPE_CHAR).evaluateAttributeExpressions(flowFile).getValue().trim();
        CSVParser parser = new CSVParserBuilder().withSeparator(separatorChar.charAt(0)).withQuoteChar(quoteChar.charAt(0)).withEscapeChar(escapeChar.charAt(0)).build();

        StopWatch stopWatch = new StopWatch(true);

        InputStream inputStream = session.read(flowFile);
        CSVReader reader = new CSVReaderBuilder(new InputStreamReader(inputStream)).withCSVParser(parser).build();
        try {
            flowFile = session.write(flowFile, out -> {
                CSVWriter writer = new CSVWriter(new OutputStreamWriter(out), separatorChar.charAt(0), quoteChar.charAt(0), escapeChar.charAt(0), CSVWriter.DEFAULT_LINE_END);

                reader.forEach(row -> {
                    String[] cleanLine = new String[row.length];

                    for (int i = 0; i < row.length; i++) {
                        cleanLine[i] = StringUtils.replaceAll(row[i], "\r\n|\n|\r", " ");
                    }
                    writer.writeNext(cleanLine);
                    writer.flushQuietly();
                });
                writer.close();
            });
            reader.close();
        } catch (Exception ex) {
            logger.error("Error CSV processing", ex);
            logger.info("Transferred {} to 'failure'", new Object[]{flowFile});
            session.getProvenanceReporter().modifyContent(flowFile, stopWatch.getElapsed(TimeUnit.MILLISECONDS));
            session.transfer(flowFile, REL_FAILURE);
            return;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        logger.info("Transferred {} to 'success'", new Object[]{flowFile});
        session.getProvenanceReporter().modifyContent(flowFile, stopWatch.getElapsed(TimeUnit.MILLISECONDS));
        session.transfer(flowFile, REL_SUCCESS);
    }
}