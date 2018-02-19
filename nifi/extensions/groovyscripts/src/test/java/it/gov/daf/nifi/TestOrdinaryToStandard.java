/*
 * Copyright 2016-2017 BatchIQ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.gov.daf.nifi;

import org.apache.commons.io.IOUtils;
import org.apache.nifi.processors.script.ExecuteScript;
import org.apache.nifi.script.ScriptingComponentUtils;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestOrdinaryToStandard extends BaseScriptTest {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Demonstrates transforming the JSON object in an incoming FlowFile to output
     */
    @Test
    public void testOrdinaryToStandard() throws Exception {
        final TestRunner runner = TestRunners.newTestRunner(new ExecuteScript());
        runner.setValidateExpressionUsage(false);
        runner.setProperty(SCRIPT_ENGINE, "Groovy");
        runner.setProperty(ScriptingComponentUtils.SCRIPT_FILE, "src/main/resources/scripts/standard/ordinaryToStandard.groovy");
        runner.assertValid();

        ClassLoader classLoader = getClass().getClassLoader();

        Map<String, String> attributes = new HashMap<>();
        attributes.put("metadata.table.fieldStructure", IOUtils.toString(classLoader.getResourceAsStream("standard/metadata.table.fieldStructure")));

        File standardJsonFile = new File(classLoader.getResource("standard/standard.json").toURI());
        runner.setProperty("standardJsonObjectFile", standardJsonFile.getAbsolutePath());

        runner.enqueue(classLoader.getResourceAsStream("standard/ordinary.json"), attributes);
        runner.run();

        final List<MockFlowFile> successFlowFiles = runner.getFlowFilesForRelationship("success");
        MockFlowFile result = successFlowFiles.get(0);
        result.assertContentEquals(classLoader.getResourceAsStream("standard/standard-merged.json"));
    }
}