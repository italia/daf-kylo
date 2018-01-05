package it.gov.daf.nifi;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.nifi.processors.script.ExecuteScript;
import org.apache.nifi.processors.script.InvokeScriptedProcessor;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;

import org.junit.BeforeClass;


/**
 * Adapted from Apache NiFi BaseScriptTest, see https://github.com/apache/nifi/blob/rel/nifi-1.0.0/nifi-nar-bundles/nifi-scripting-bundle/nifi-scripting-processors/src/test/java/org/apache/nifi/processors/script/BaseScriptTest.java
 *
 * An abstract class with common methods, variables, etc. used by scripting processor unit tests
 */
public abstract class BaseScriptTest {

    protected final String TEST_RESOURCE_LOCATION = "target/test/resources/";
    protected TestRunner runner;
    protected final String SCRIPT_ENGINE = "Script Engine";

    /**
     * Copies all scripts to the target directory because when they are compiled they can leave unwanted .class files.
     *
     * @throws Exception Any error encountered while testing
     */
    @BeforeClass
    public static void setupBeforeClass() throws Exception {
        FileUtils.copyDirectory(new File("src/main/groovy"), new File("target/src/main/groovy"));
    }

    public void setupExecuteScript() throws Exception {
        final ExecuteScript executeScript = new ExecuteScript() {
            {
                // Initialize list of PropertyDescriptors
                getSupportedPropertyDescriptors();
            }
        };
        runner = TestRunners.newTestRunner(executeScript);
    }

    public void setupInvokeScriptProcessor() throws Exception {
        final InvokeScriptedProcessor invokeScriptedProcessor = new InvokeScriptedProcessor() {
            {
                // Initialize list of PropertyDescriptors
                getSupportedPropertyDescriptors();
            }
        };
        runner = TestRunners.newTestRunner(invokeScriptedProcessor);
    }

    public String getFileContentsAsString(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException ioe) {
            return null;
        }
    }
}

