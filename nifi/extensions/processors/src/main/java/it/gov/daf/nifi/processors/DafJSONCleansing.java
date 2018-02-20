package it.gov.daf.nifi.processors;

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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@EventDriven
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"json", "daf"})
@CapabilityDescription("Operate some JSON cleansing before ingestion")
public class DafJSONCleansing extends AbstractProcessor {

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

    public DafJSONCleansing() {
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

        session.read(flowFile, in -> {
            try {
                String json = IOUtils.toString(in, StandardCharsets.UTF_8);
                objectMapper.readTree(json);
            } catch (Exception e) {
                valid.set(false);
                logger.debug("Failed to validate {} against schema due to {}", new Object[]{flowFile, e});
            }
        });

        if (valid.get()) {
            logger.debug("Successfully validated {} against schema; routing to 'valid'", new Object[]{flowFile});
            session.getProvenanceReporter().route(flowFile, REL_SUCCESS);
            session.transfer(flowFile, REL_SUCCESS);
        } else {
            logger.debug("Failed to validate {} against schema; routing to 'invalid'", new Object[]{flowFile});
            session.getProvenanceReporter().route(flowFile, REL_FAILURE);
            session.transfer(flowFile, REL_FAILURE);
        }

    }
}