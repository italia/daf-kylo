
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
    "type",
    "param"
})
public class Constr {

    /**
     * The Type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("type")
    private String type = "";
    /**
     * The Param Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("param")
    private String param = "";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The Type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * The Type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The Param Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("param")
    public String getParam() {
        return param;
    }

    /**
     * The Param Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("param")
    public void setParam(String param) {
        this.param = param;
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
        return new ToStringBuilder(this).append("type", type).append("param", param).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(additionalProperties).append(type).append(param).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Constr) == false) {
            return false;
        }
        Constr rhs = ((Constr) other);
        return new EqualsBuilder().append(additionalProperties, rhs.additionalProperties).append(type, rhs.type).append(param, rhs.param).isEquals();
    }

}
