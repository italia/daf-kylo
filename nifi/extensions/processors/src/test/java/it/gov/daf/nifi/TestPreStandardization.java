package it.gov.daf.nifi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import it.gov.daf.nifi.processors.DafPreStandardization;
import it.gov.daf.nifi.processors.models.Transformations;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.dreamhead.moco.Moco.httpServer;
import static com.github.dreamhead.moco.Runner.runner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TestPreStandardization {

    private Logger log = LoggerFactory.getLogger(getClass());

    private TestRunner createRunner(String catalogPath, String dsName, String username, String pwd){
        final TestRunner runner = TestRunners.newTestRunner(new DafPreStandardization());
        runner.setValidateExpressionUsage(false);
        runner.setProperty(DafPreStandardization.CATALOG_GET_PATH, catalogPath);
        runner.setProperty(DafPreStandardization.DATASET_NAME, dsName);
        runner.setProperty(DafPreStandardization.USERNAME, username);
        runner.setProperty(DafPreStandardization.PASSWORD, pwd);
        runner.assertValid();
        return runner;
    }

    @Test
    public void testCatalogInaccessible() {
        final TestRunner testRunner =
                createRunner("http://localhost:9000/", "test", "test", "test");

        ProcessSession session = testRunner.getProcessSessionFactory().createSession();
        FlowFile ff = session.create();
        testRunner.enqueue(ff);
        testRunner.run();

        testRunner.assertTransferCount(DafPreStandardization.REL_FAILURE, 1);
    }


    @Test
    public void testCatalogWorking() throws IOException, URISyntaxException {
        final URI uri = getClass().getResource("/json/standardization-dataschema.json").toURI();
        String body = new String(Files.readAllBytes(Paths.get(uri)));

        HttpServer server = httpServer(9000);
        server.response(body);
        Runner runner = runner(server);
        runner.start();

        try {
            final TestRunner testRunner =
                    createRunner("http://localhost:9000/", "test", "test", "test");

            ProcessSession session = testRunner.getProcessSessionFactory().createSession();
            FlowFile ff = session.create();
            testRunner.enqueue(ff);
            testRunner.run();

            testRunner.assertTransferCount(DafPreStandardization.REL_SUCCESS, 1);

            final String sParamaters = testRunner.getFlowFilesForRelationship(DafPreStandardization.REL_SUCCESS)
                    .get(0)
                    .getAttribute(DafPreStandardization.OUTPUT_JOB_PARAMS);
            log.info("the sParamaters are {}", sParamaters);
            assertThat(sParamaters.length(), notNullValue());

            ObjectMapper mapper = new ObjectMapper();
            final Transformations transformations = mapper.readValue(sParamaters, Transformations.class);

            transformations.getTransformationSteps().forEach(System.out::println);

            assertThat(transformations.getTransformationSteps().size(), is(6));


        } finally {
            runner.stop();
        }


    }

}
