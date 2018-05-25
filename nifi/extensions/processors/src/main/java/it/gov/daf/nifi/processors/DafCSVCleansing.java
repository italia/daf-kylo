package it.gov.daf.nifi.processors;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
        .defaultValue(",")
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .expressionLanguageSupported(true)
        .build();

    public static final PropertyDescriptor QUOTE_CHAR = new PropertyDescriptor.Builder()
        .name("Quote Char")
        .description("The CSV Quote Char")
        .required(true)
        .defaultValue("\"")
        .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
        .expressionLanguageSupported(true)
        .build();

    public static final PropertyDescriptor ESCAPE_CHAR = new PropertyDescriptor.Builder()
        .name("Escape Char")
        .description("The CSV Escape Char")
        .required(true)
        .defaultValue("\\")
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

        String separatorString = context.getProperty(SEPARATOR_CHAR).evaluateAttributeExpressions(flowFile).getValue();
        String quoteString = context.getProperty(QUOTE_CHAR).evaluateAttributeExpressions(flowFile).getValue();
        String escapeString = context.getProperty(ESCAPE_CHAR).evaluateAttributeExpressions(flowFile).getValue();

        if (StringUtils.startsWith(separatorString, "\\")) {
            separatorString = StringEscapeUtils.unescapeJava(separatorString);
        }

        if (StringUtils.startsWith(quoteString, "\\")) {
            quoteString = StringEscapeUtils.unescapeJava(quoteString);
        }

        if (StringUtils.startsWith(escapeString, "\\")) {
            escapeString = StringEscapeUtils.unescapeJava(escapeString);
        }

        char separatorChar = separatorString.charAt(0);
        char quoteChar = quoteString.charAt(0);
        char escapeChar = escapeString.charAt(0);

        CSVParser parser = new CSVParserBuilder().withSeparator(separatorChar).withQuoteChar(quoteChar).withEscapeChar(escapeChar).build();
        StopWatch stopWatch = new StopWatch(true);

        try {
            flowFile = session.write(flowFile, (in, out) -> {
//                CSVReader reader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(in))).withCSVParser(parser).build();
//                CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(out)), separatorChar, quoteChar, escapeChar, CSVWriter.DEFAULT_LINE_END);
//                final int[] count = {0};
//                reader.forEach(row -> {
//                    String[] cleanLine = new String[row.length];
//
//                    for (int i = 0; i < row.length; i++) {
//                        cleanLine[i] = StringUtils.replaceAll(row[i], "\r\n|\n|\r", " ");
//                    }
//                    writer.writeNext(cleanLine);
//                    try {
//                        count[0]++;
////                        if(count[0]%10000==0){
//                            logger.info(" # "+count[0]);
////                    }
//                        if(count[0]%1000==0){
////                            System.out.println(" # "+count[0]);
//
//                            logger.info( " TOTAL MEMORY :"+ Runtime.getRuntime().totalMemory());
//                            logger.info(" FREE MEMORY :"+ Runtime.getRuntime().freeMemory());
//                            logger.info(" faccio il flush all riga "+count[0]);
//                            writer.flush();
//                        }
//                    } catch (Exception e) {
//                        logger.info(" # "+count[0]);
//                        logger.info(" riga: " + row);
//                        e.printStackTrace();
//                    }
//                });
//
//                writer.close();
//primo test
//                CSVReader reader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(in))).withCSVParser(parser).build();
//                CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(out)), separatorChar, quoteChar, escapeChar, CSVWriter.DEFAULT_LINE_END);
//                int j = 0;
//                String[] row=null;
//                String[] pre_row=null;
//                try {
//                    while ((row = reader.readNext()) != null) {
//                        String[] cleanLine = new String[row.length];
//                        logger.info("row.length " + row.length);
//                        logger.info("row.id " + row[0]);
//                        for (int i = 0; i < row.length; i++) {
////                            cleanLine[i] = StringUtils.replaceAll(row[i], "\r\n|\n|\r", " ");
//                            try {
//                                cleanLine[i] = StringUtils.replaceAll(row[i], "\r\n|\n|\r", " ");
//                            }
//                            catch(Exception ex) {
//                                logger.error(new Integer(i).toString());
//                                logger.error(new Integer(j).toString());
//                                logger.error(row[i]);
//                                logger.error(ex.toString());
//                                logger.info(ex.getMessage());
//                            }
//
//                        }
//                        cleanLine=null;
////                        writer.writeNext(cleanLine);
//                        if (j%100==0) {
//                            logger.info("j= " + j);
////                            logger.info("row= " + Arrays.toString(row));
////                            logger.info("row.length= " + row.length);
//
//
//                        }
//                        // Every 5000 rows
//                        // force flush
////                        if ((j % 5000) == 0) {
////                            writer.flush();
////                        }
//                        j++;
//                    pre_row=row;
//                    }
//                }catch(Exception ex){
//                    logger.info(ex.getMessage());
//                    logger.error(Arrays.toString(pre_row));
//                    logger.error(Arrays.toString(row));
//
//                    //                    logger.info("j : "+j);
////                    logger.info("row : "+row);
//                }
//                writer.close();
//
//                });
                    int j = 0;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    CSVWriter writer = new CSVWriter(new BufferedWriter(new OutputStreamWriter(out)), separatorChar, quoteChar, escapeChar, CSVWriter.DEFAULT_LINE_END);
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        try {
                            String[] row = parser.parseLine(line);
                            String[] cleanLine = new String[row.length];

                            for (int i = 0; i < row.length; i++) {
                                cleanLine[i] = StringUtils.replaceAll(row[i], "\r\n|\n|\r", " ");
                            }
                            writer.writeNext(cleanLine);
                        } catch (Exception ex) {
                            logger.error("Malformed line " + line);
                            logger.error("Parsing error ", ex);
                        }
                        finally {
                            j++;
                            if(j % 1000 == 0) {
                                // flush file
                                writer.flush();
                            }
                        }
                    }

                    writer.close();
                });
        } catch (Exception ex) {
            logger.error("Error CSV processing", ex);
            logger.info("Transferred {} to 'failure'", new Object[]{flowFile});
            session.getProvenanceReporter().modifyContent(flowFile, stopWatch.getElapsed(TimeUnit.MILLISECONDS));
            session.transfer(flowFile, REL_FAILURE);
            return;
        }

        logger.info("Transferred {} to 'success'", new Object[]{flowFile});
        session.getProvenanceReporter().modifyContent(flowFile, stopWatch.getElapsed(TimeUnit.MILLISECONDS));
        session.transfer(flowFile, REL_SUCCESS);
    }
}