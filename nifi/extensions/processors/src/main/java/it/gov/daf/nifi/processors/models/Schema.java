
package it.gov.daf.nifi.processors.models;

import java.util.HashMap;
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
    "dataschema",
    "operational",
    "dcatapit"
})
public class Schema {

    @JsonProperty("dataschema")
    private Dataschema dataschema;
    @JsonProperty("operational")
    private Operational operational;
    @JsonProperty("dcatapit")
    private Dcatapit dcatapit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("dataschema")
    public Dataschema getDataschema() {
        return dataschema;
    }

    @JsonProperty("dataschema")
    public void setDataschema(Dataschema dataschema) {
        this.dataschema = dataschema;
    }

    @JsonProperty("operational")
    public Operational getOperational() {
        return operational;
    }

    @JsonProperty("operational")
    public void setOperational(Operational operational) {
        this.operational = operational;
    }

    @JsonProperty("dcatapit")
    public Dcatapit getDcatapit() {
        return dcatapit;
    }

    @JsonProperty("dcatapit")
    public void setDcatapit(Dcatapit dcatapit) {
        this.dcatapit = dcatapit;
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
        return new ToStringBuilder(this).append("dataschema", dataschema).append("operational", operational).append("dcatapit", dcatapit).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dcatapit).append(operational).append(additionalProperties).append(dataschema).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Schema) == false) {
            return false;
        }
        Schema rhs = ((Schema) other);
        return new EqualsBuilder().append(dcatapit, rhs.dcatapit).append(operational, rhs.operational).append(additionalProperties, rhs.additionalProperties).append(dataschema, rhs.dataschema).isEquals();
    }

}
