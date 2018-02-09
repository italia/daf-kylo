package it.gov.daf.nifi.processors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
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

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@EventDriven
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"json", "daf"})
@CapabilityDescription("Transform a JSON Array in a multiline JSON file")
public class DafJSONArrayToMultiline extends AbstractProcessor {

    // Relationships
    public static final Relationship REL_SUCCESS = new Relationship.Builder()
        .name("success")
        .description("Successful result.")
        .build();

    public static final Relationship REL_FAILURE = new Relationship.Builder()
        .name("failure")
        .description("Error in the JSON file")
        .build();

    private final Set<Relationship> relationships;

    /**
     * List of properties
     */
    private List<PropertyDescriptor> propDescriptors;

    public DafJSONArrayToMultiline() {
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
        FlowFile flowFile = session.get();
        if (flowFile == null) {
            return;
        }

        final ComponentLog logger = getLogger();
        final AtomicBoolean valid = new AtomicBoolean(true);
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonFactory jsonFactory = new JsonFactory(objectMapper);

        flowFile = session.write(flowFile, (in, out) -> {
            try {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                JsonParser jsonParser = jsonFactory.createParser(in);
                JsonToken jsonToken = jsonParser.nextToken();
                if (jsonToken == JsonToken.START_ARRAY) {
                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                        TreeNode treeNode = jsonParser.readValueAsTree();
                        bw.write(treeNode.toString());
                        bw.newLine();
                    }
                    bw.close();
                } else {
                    in.reset();
                    IOUtils.copy(in, out);
                }
            } catch (Exception e) {
                logger.error("Error parsing JSON", e);
                in.reset();
                IOUtils.copy(in, out);
                valid.set(false);
            }
        });

        if (valid.get()) {
            logger.debug("Successfully validated {}; routing to 'valid'", new Object[]{flowFile});
            session.getProvenanceReporter().route(flowFile, REL_SUCCESS);
            session.transfer(flowFile, REL_SUCCESS);
        } else {
            logger.debug("Failed to validate {}; routing to 'invalid'", new Object[]{flowFile});
            session.getProvenanceReporter().route(flowFile, REL_FAILURE);
            session.transfer(flowFile, REL_FAILURE);
        }

    }
}