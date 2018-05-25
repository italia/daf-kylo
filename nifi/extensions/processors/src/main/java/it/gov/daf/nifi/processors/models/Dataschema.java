
package it.gov.daf.nifi.processors.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "avro",
    "flatSchema",
    "kyloSchema"
})
public class Dataschema {

    @JsonProperty("avro")
    private Avro avro;
    @JsonProperty("flatSchema")
    private List<FlatSchema> flatSchema = new ArrayList<FlatSchema>();
    /**
     * The Kyloschema Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("kyloSchema")
    private String kyloSchema = "";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("avro")
    public Avro getAvro() {
        return avro;
    }

    @JsonProperty("avro")
    public void setAvro(Avro avro) {
        this.avro = avro;
    }

    @JsonProperty("flatSchema")
    public List<FlatSchema> getFlatSchema() {
        return flatSchema;
    }

    @JsonProperty("flatSchema")
    public void setFlatSchema(List<FlatSchema> flatSchema) {
        this.flatSchema = flatSchema;
    }

    /**
     * The Kyloschema Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("kyloSchema")
    public String getKyloSchema() {
        return kyloSchema;
    }

    /**
     * The Kyloschema Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("kyloSchema")
    public void setKyloSchema(String kyloSchema) {
        this.kyloSchema = kyloSchema;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("avro", avro).append("flatSchema", flatSchema).append("kyloSchema", kyloSchema).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(kyloSchema).append(additionalProperties).append(flatSchema).append(avro).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Dataschema) == false) {
            return false;
        }
        Dataschema rhs = ((Dataschema) other);
        return new EqualsBuilder().append(kyloSchema, rhs.kyloSchema).append(additionalProperties, rhs.additionalProperties).append(flatSchema, rhs.flatSchema).append(avro, rhs.avro).isEquals();
    }

}
