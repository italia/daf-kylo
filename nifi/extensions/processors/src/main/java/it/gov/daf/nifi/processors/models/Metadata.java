
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
    "semantics",
    "desc",
    "tag",
    "field_type",
    "constr",
    "required",
    "cat"
})
public class Metadata {

    @JsonProperty("semantics")
    private Semantics semantics;
    /**
     * The Desc Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("desc")
    private String desc = "";
    @JsonProperty("tag")
    private List<Object> tag = new ArrayList<Object>();
    /**
     * The Field_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("field_type")
    private String fieldType = "";
    @JsonProperty("constr")
    private List<Constr> constr = new ArrayList<Constr>();
    /**
     * The Required Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("required")
    private Integer required = 0;
    /**
     * The Cat Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("cat")
    private String cat = "";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("semantics")
    public Semantics getSemantics() {
        return semantics;
    }

    @JsonProperty("semantics")
    public void setSemantics(Semantics semantics) {
        this.semantics = semantics;
    }

    /**
     * The Desc Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    /**
     * The Desc Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @JsonProperty("tag")
    public List<Object> getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(List<Object> tag) {
        this.tag = tag;
    }

    /**
     * The Field_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("field_type")
    public String getFieldType() {
        return fieldType;
    }

    /**
     * The Field_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("field_type")
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @JsonProperty("constr")
    public List<Constr> getConstr() {
        return constr;
    }

    @JsonProperty("constr")
    public void setConstr(List<Constr> constr) {
        this.constr = constr;
    }

    /**
     * The Required Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("required")
    public Integer getRequired() {
        return required;
    }

    /**
     * The Required Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("required")
    public void setRequired(Integer required) {
        this.required = required;
    }

    /**
     * The Cat Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("cat")
    public String getCat() {
        return cat;
    }

    /**
     * The Cat Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("cat")
    public void setCat(String cat) {
        this.cat = cat;
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
        return new ToStringBuilder(this).append("semantics", semantics).append("desc", desc).append("tag", tag).append("fieldType", fieldType).append("constr", constr).append("required", required).append("cat", cat).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(constr).append(cat).append(semantics).append(tag).append(additionalProperties).append(fieldType).append(required).append(desc).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metadata) == false) {
            return false;
        }
        Metadata rhs = ((Metadata) other);
        return new EqualsBuilder().append(constr, rhs.constr).append(cat, rhs.cat).append(semantics, rhs.semantics).append(tag, rhs.tag).append(additionalProperties, rhs.additionalProperties).append(fieldType, rhs.fieldType).append(required, rhs.required).append(desc, rhs.desc).isEquals();
    }

}
