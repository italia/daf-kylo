package it.gov.daf.nifi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import it.gov.daf.nifi.processors.models.FlatSchema;
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
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestMetaCatalogParsing {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void readResource() throws IOException, URISyntaxException {
        final URI uri = getClass().getResource("/json/standardization-dataschema.json").toURI();
        assertThat(Files.lines(Paths.get(uri)).count(), not(0));
    }

    @Test
    public void readFlatSchema() throws IOException {
        final URL url = getClass().getResource("/json/standardization-dataschema.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode node = objectMapper.readTree(url);

        final JsonNode flatschema = node.at("/dataschema/flatSchema");
        assertThat(flatschema.size(), not(0));
        assertThat(flatschema.getNodeType(), is(JsonNodeType.ARRAY));

        final Stream<FlatSchema> stream = StreamSupport.stream(flatschema.spliterator(), false)
                .map(n -> {
                    try {
                        return objectMapper.treeToValue(n, FlatSchema.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull);
        assertThat(stream.count(), not(0));
    }

    @Test
    public void readIngestionPipeline() throws IOException {
        final URL url = getClass().getResource("/json/standardization-dataschema.json");
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root = objectMapper.readTree(url);

        final JsonNode node = root.at("/operational/ingestion_pipeline");

        assertThat(node.size(), not(0));
        assertThat(node.getNodeType(), is(JsonNodeType.ARRAY));

        final ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {});
        final List<String> list = reader.readValue(node);
        assertThat(list.size(), not(0));

        System.out.println(list);
    }
}
