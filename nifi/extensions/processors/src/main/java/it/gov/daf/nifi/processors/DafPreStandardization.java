package it.gov.daf.nifi.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import it.gov.daf.nifi.processors.models.FlatSchema;
import it.gov.daf.nifi.processors.models.TransformationStep;
import it.gov.daf.nifi.processors.models.Trasnformations;
import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.logging.ComponentLog;
import org.apache.nifi.processor.*;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.util.StopWatch;
import org.asynchttpclient.*;

import static org.asynchttpclient.Dsl.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static java.util.stream.Collectors.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@EventDriven
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@Tags({"pre-standardization", "daf"})
@CapabilityDescription("Prepare the parameters to be passed at the standardization job")
public class DafPreStandardization extends AbstractProcessor {

    //add http client pool plus the hook for the shutdown
    //TODO check with Fabian
    private static final AsyncHttpClient httpClient = asyncHttpClient(
            config()
                    .setFollowRedirect(true)
                    .setThreadPoolName("daf-prestandardization")
                    .setUserAgent("daf-prestandardization")
                    .setMaxConnections(20)
                    .setMaxConnectionsPerHost(10)
    );

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public static final Relationship REL_SUCCESS = new Relationship.Builder()
            .name("success")
            .description("Successful result")
            .build();

    public static final Relationship REL_FAILURE = new Relationship.Builder()
            .name("failure")
            .description("Error in the dataset metadata")
            .build();

    private final Set<Relationship> relationships;

    //FIXME maybe we can use the dataset name
    public static final PropertyDescriptor DATASET_NAME = new PropertyDescriptor.Builder()
            .name("dataset name")
            .description("The unique name of the dataset to process")
            .required(true)
            .defaultValue("")
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .expressionLanguageSupported(true)
            .build();

    public static final PropertyDescriptor CATALOG_GET_PATH = new PropertyDescriptor.Builder()
            .name("catalog get path")
            .description("The path of the get used to retrieve the dataset schema from the catalog manager")
            .required(true)
            .addValidator(StandardValidators.URL_VALIDATOR)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .expressionLanguageSupported(false)
            .build();

    public static final PropertyDescriptor USERNAME = new PropertyDescriptor.Builder()
            .name("username")
            .description("the username of the user used to perform the get to the catalog manager")
            .required(true)
            .defaultValue("")
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .expressionLanguageSupported(true)
            .build();

    public static final PropertyDescriptor PASSWORD = new PropertyDescriptor.Builder()
            .name("password")
            .description("the password of the user used to perform the get to the catalog manager")
            .required(true)
            .defaultValue("")
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .expressionLanguageSupported(true)
            .build();

    private List<PropertyDescriptor> propsDescriptor;

    public static final String OUTPUT_JOB_PARAMS = "job.params";

    public DafPreStandardization() {
        relationships = Stream.of(REL_SUCCESS, REL_FAILURE)
                .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    }

    @Override
    protected void init(ProcessorInitializationContext context) {
        super.init(context);
        //create the list of the properties
        propsDescriptor = Stream.of(DATASET_NAME, CATALOG_GET_PATH, USERNAME, PASSWORD)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    @Override
    public Set<Relationship> getRelationships() {
        return relationships;
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return propsDescriptor;
    }

    @Override
    public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {
        StopWatch stopWatch = new StopWatch(true);
        ComponentLog logger = getLogger();

        FlowFile flowFile = session.get();
        if (flowFile == null) {
            logger.error("null flow file");
            throw new ProcessException("Flow file is null");
        }

        final String datasetName = context.getProperty(DATASET_NAME)
                .evaluateAttributeExpressions(flowFile).getValue();

        String catalogGetPath = context.getProperty(CATALOG_GET_PATH)
                .evaluateAttributeExpressions(flowFile).getValue();
        if (!catalogGetPath.endsWith("/"))
            catalogGetPath += "/";

        final String username = context.getProperty(USERNAME)
                .evaluateAttributeExpressions(flowFile).getValue();
        final String password = context.getProperty(PASSWORD)
                .evaluateAttributeExpressions(flowFile).getValue();


        //perform the call to get the dataset schema
        final String authorization = "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(Charset.forName("UTF-8")));
        final String url = catalogGetPath + datasetName;

        //Parse the flatschema
        final ObjectMapper mapper = new ObjectMapper();
        try {

            final CompletableFuture<Response> fResponse = httpClient.prepareGet(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", authorization).execute().toCompletableFuture();

            //FIXME naive completion
            final Response response = fResponse.join();

            if (response.getStatusCode() < 200 && response.getStatusCode() > 399){
                logger.error("Response Status " + response.getStatusCode() + " for request " + url);
                throw new ProcessException("Response Status " + response.getStatusCode() + " for request " + url);
            }

            //parse the response and get the flat schema
            final JsonNode root = mapper.readTree(response.getResponseBodyAsBytes());
            final Stream<FlatSchema> flatSchemaStream = getFlatSchemas(mapper, root);

            if (flatSchemaStream.count() == 0){
                logger.error("cannot find flat schema for dataset ");
                throw new ProcessException("Error parsing /dataschema/flatSchema");
            }

            final List<String> sTransformations = getTransformations(mapper, root);
            final List<TransformationStep> transformationSteps =
                    Trasnformations.gensTransformations(sTransformations, flatSchemaStream);

            if (!transformationSteps.isEmpty()){
                flowFile = session.putAttribute(flowFile, OUTPUT_JOB_PARAMS, mapper.writeValueAsString(transformationSteps));
            }
            logger.info("added transformationSteps {} to flow", transformationSteps.toArray());
            session.getProvenanceReporter().fetch(flowFile, datasetName, stopWatch.getElapsed(TimeUnit.MILLISECONDS));
            session.transfer(flowFile, REL_SUCCESS);

        } catch (Exception e) {
            logger.error("Error parsing response body {}" + e.getMessage(), e);
            session.getProvenanceReporter().fetch(flowFile, datasetName, stopWatch.getElapsed(TimeUnit.MILLISECONDS));
            session.transfer(flowFile, REL_FAILURE);
        }
    }


    private List<String> getTransformations(ObjectMapper mapper, JsonNode root) throws IOException {
        final JsonNode node = root.at("/operational/ingestion_pipeline");
        if (node.isMissingNode()) {
            return new ArrayList<>();
        } else {
            final ObjectReader reader = mapper.readerFor(new TypeReference<List<String>>() {
            });
            return reader.readValue(node);
        }
    }

    private Stream<FlatSchema> getFlatSchemas(ObjectMapper mapper, JsonNode root) {
        final JsonNode list = root.at("/dataschema/flatSchema");

        return StreamSupport.stream(list.spliterator(), false)
                .map(n -> {
                    try {
                        return mapper.treeToValue(n, FlatSchema.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull);
    }
}
