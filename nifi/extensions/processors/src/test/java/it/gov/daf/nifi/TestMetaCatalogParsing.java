package it.gov.daf.nifi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.daf.nifi.processors.DafPreStandardization;
import it.gov.daf.nifi.processors.models2.FlatSchema;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TestMetaCatalogParsing {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void readResource() throws IOException, URISyntaxException {
        final URI uri = getClass().getResource("/json/standardization-dataschema.json").toURI();
        assertThat(Files.lines(Paths.get(uri)).count(), not(0));
    }

    @Test
    public void validateTransformations1() throws IOException {
        getTransformationsTest("/json/movimenti-turistici.json");
    }


    @Test
    public void validateTranformation2() throws IOException {
        getTransformationsTest("/json/standardization-dataschema.json");
    }


    private void getTransformationsTest(String path) throws IOException {
        final URL url = getClass().getResource(path);
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root = objectMapper.readTree(url);

        final List<String> transformations = DafPreStandardization.getTransformations(objectMapper, root);
        assertThat(transformations.size(), not(0));
        System.out.println(transformations);
    }

    @Test
    public void getFlatSchemaTest1() throws IOException {
        getFlatSchemaTest("/json/standardization-dataschema.json");
    }

    @Test
    public void getFlatSchemaTest2() throws IOException {
        getFlatSchemaTest("/json/movimenti-turistici.json");
    }


    private void getFlatSchemaTest(String path) throws IOException {
        final URL url = getClass().getResource(path);
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root = objectMapper.readTree(url);

        final Stream<FlatSchema> flatSchema = DafPreStandardization.getFlatSchema(objectMapper, root);
        assertThat(flatSchema.count(), not(0));
    }

    @Test
    public void getPhysicalUriTest() throws IOException {
        final URL url = getClass().getResource("/json/standardization-dataschema.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root = objectMapper.readTree(url);

        final String physicalUri = DafPreStandardization.getPhysicalUri(objectMapper, root);

        assertThat(physicalUri, notNullValue());
    }
}
