
package it.gov.daf.nifi.processors.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "semantics",
    "tag",
    "field_type",
    "constr",
    "required",
    "cat",
    "desc"
})
public class Metadata {

    @JsonProperty("semantics")
    private Semantics semantics;
    @JsonProperty("tag")
    private List<Object> tag = null;
    @JsonProperty("field_type")
    private String fieldType;
    @JsonProperty("constr")
    private List<Constr> constr = null;
    @JsonProperty("required")
    private Integer required;
    @JsonProperty("cat")
    private String cat;
    @JsonProperty("desc")
    private String desc;
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

    @JsonProperty("tag")
    public List<Object> getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(List<Object> tag) {
        this.tag = tag;
    }

    @JsonProperty("field_type")
    public String getFieldType() {
        return fieldType;
    }

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

    @JsonProperty("required")
    public Integer getRequired() {
        return required;
    }

    @JsonProperty("required")
    public void setRequired(Integer required) {
        this.required = required;
    }

    @JsonProperty("cat")
    public String getCat() {
        return cat;
    }

    @JsonProperty("cat")
    public void setCat(String cat) {
        this.cat = cat;
    }

    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
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
        return "Metadata{" +
                "semantics=" + semantics +
                ", tag=" + tag +
                ", fieldType='" + fieldType + '\'' +
                ", constr=" + constr +
                ", required=" + required +
                ", cat='" + cat + '\'' +
                ", desc='" + desc + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
