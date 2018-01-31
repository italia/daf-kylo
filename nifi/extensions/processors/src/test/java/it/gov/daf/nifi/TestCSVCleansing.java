package it.gov.daf.nifi;

import it.gov.daf.nifi.processors.DafCSVCleansing;

import org.apache.commons.io.IOUtils;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class TestCSVCleansing {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testCSVCleansing() throws Exception {
        final TestRunner runner = TestRunners.newTestRunner(new DafCSVCleansing());
        runner.setValidateExpressionUsage(false);
        runner.setProperty(DafCSVCleansing.SEPARATOR_CHAR, "|");
        runner.setProperty(DafCSVCleansing.QUOTE_CHAR, "\"");
        runner.setProperty(DafCSVCleansing.ESCAPE_CHAR, "\\");
        runner.assertValid();

        ClassLoader classLoader = getClass().getClassLoader();
        String testCsv = "";
        try {
            testCsv = IOUtils.toString(classLoader.getResourceAsStream("csv/test.csv"));
        } catch (IOException e) {
            log.error("", e);
        }

        byte[] flowContent = testCsv.getBytes();

        runner.enqueue(flowContent);
        runner.run();

        final List<MockFlowFile> successFlowFiles = runner.getFlowFilesForRelationship("success");
        MockFlowFile result = successFlowFiles.get(0);
        result.assertContentEquals(classLoader.getResourceAsStream("csv/result.csv"));
    }

    @Test
    public void testCSVCleansingWithCharEscaping() throws Exception {
        final TestRunner runner = TestRunners.newTestRunner(new DafCSVCleansing());
        runner.setValidateExpressionUsage(false);
        runner.setProperty(DafCSVCleansing.SEPARATOR_CHAR, "|");
        runner.setProperty(DafCSVCleansing.QUOTE_CHAR, "\\\"");
        runner.setProperty(DafCSVCleansing.ESCAPE_CHAR, "\\\\");
        runner.assertValid();

        ClassLoader classLoader = getClass().getClassLoader();
        String testCsv = "";
        try {
            testCsv = IOUtils.toString(classLoader.getResourceAsStream("csv/test.csv"));
        } catch (IOException e) {
            log.error("", e);
        }

        byte[] flowContent = testCsv.getBytes();

        runner.enqueue(flowContent);
        runner.run();

        final List<MockFlowFile> successFlowFiles = runner.getFlowFilesForRelationship("success");
        MockFlowFile result = successFlowFiles.get(0);
        result.assertContentEquals(classLoader.getResourceAsStream("csv/result.csv"));
    }
}