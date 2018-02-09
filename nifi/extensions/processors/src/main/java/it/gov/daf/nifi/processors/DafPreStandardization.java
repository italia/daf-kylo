package it.gov.daf.nifi.processors;

import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

@EventDriven
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"pre-standardization", "daf"})
@CapabilityDescription("Prepare the parameters to be passed at the standardization job")
public class DafPreStandardization extends AbstractProcessor {

    private static final Relationship REL_SUCCESS = new Relationship.Builder()
            .name("success")
            .description("Successful result")
            .build();

    private static final Relationship REL_FAILURE = new Relationship.Builder()
            .name("failure")
            .description("Error in the dataset metadata")
            .build();

    private final Set<Relationship> relationships;

    private List<PropertyDescriptor> propsDescriptor;

    public DafPreStandardization() {
        relationships = Stream.of(REL_SUCCESS, REL_FAILURE)
                .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    }

    @Override
    protected void init(ProcessorInitializationContext context) {
        super.init(context);

        //create the list of the properties

    }

    @Override
    public void onTrigger(ProcessContext processContext, ProcessSession processSession) throws ProcessException {

    }
}
