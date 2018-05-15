
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
    "dataset_type",
    "read_type",
    "is_std",
    "logical_uri",
    "theme",
    "subtheme",
    "group_own",
    "storage_info",
    "physical_uri",
    "input_src",
    "ingestion_pipeline"
})
public class Operational {

    /**
     * The Dataset_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("dataset_type")
    private String datasetType = "";
    /**
     * The Read_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("read_type")
    private String readType = "";
    /**
     * The Is_std Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("is_std")
    private Boolean isStd = false;
    /**
     * The Logical_uri Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("logical_uri")
    private String logicalUri = "";
    /**
     * The Theme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("theme")
    private String theme = "";
    /**
     * The Subtheme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("subtheme")
    private String subtheme = "";
    /**
     * The Group_own Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("group_own")
    private String groupOwn = "";
    @JsonProperty("storage_info")
    private StorageInfo storageInfo;
    /**
     * The Physical_uri Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("physical_uri")
    private String physicalUri = "";
    @JsonProperty("input_src")
    private InputSrc inputSrc;
    @JsonProperty("ingestion_pipeline")
    private List<String> ingestionPipeline = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The Dataset_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("dataset_type")
    public String getDatasetType() {
        return datasetType;
    }

    /**
     * The Dataset_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("dataset_type")
    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    /**
     * The Read_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("read_type")
    public String getReadType() {
        return readType;
    }

    /**
     * The Read_type Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("read_type")
    public void setReadType(String readType) {
        this.readType = readType;
    }

    /**
     * The Is_std Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("is_std")
    public Boolean getIsStd() {
        return isStd;
    }

    /**
     * The Is_std Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("is_std")
    public void setIsStd(Boolean isStd) {
        this.isStd = isStd;
    }

    /**
     * The Logical_uri Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("logical_uri")
    public String getLogicalUri() {
        return logicalUri;
    }

    /**
     * The Logical_uri Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("logical_uri")
    public void setLogicalUri(String logicalUri) {
        this.logicalUri = logicalUri;
    }

    /**
     * The Theme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("theme")
    public String getTheme() {
        return theme;
    }

    /**
     * The Theme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("theme")
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * The Subtheme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("subtheme")
    public String getSubtheme() {
        return subtheme;
    }

    /**
     * The Subtheme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("subtheme")
    public void setSubtheme(String subtheme) {
        this.subtheme = subtheme;
    }

    /**
     * The Group_own Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("group_own")
    public String getGroupOwn() {
        return groupOwn;
    }

    /**
     * The Group_own Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("group_own")
    public void setGroupOwn(String groupOwn) {
        this.groupOwn = groupOwn;
    }

    @JsonProperty("storage_info")
    public StorageInfo getStorageInfo() {
        return storageInfo;
    }

    @JsonProperty("storage_info")
    public void setStorageInfo(StorageInfo storageInfo) {
        this.storageInfo = storageInfo;
    }

    /**
     * The Physical_uri Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("physical_uri")
    public String getPhysicalUri() {
        return physicalUri;
    }

    /**
     * The Physical_uri Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("physical_uri")
    public void setPhysicalUri(String physicalUri) {
        this.physicalUri = physicalUri;
    }

    @JsonProperty("input_src")
    public InputSrc getInputSrc() {
        return inputSrc;
    }

    @JsonProperty("input_src")
    public void setInputSrc(InputSrc inputSrc) {
        this.inputSrc = inputSrc;
    }

    @JsonProperty("ingestion_pipeline")
    public List<String> getIngestionPipeline() {
        return ingestionPipeline;
    }

    @JsonProperty("ingestion_pipeline")
    public void setIngestionPipeline(List<String> ingestionPipeline) {
        this.ingestionPipeline = ingestionPipeline;
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
        return new ToStringBuilder(this).append("datasetType", datasetType).append("readType", readType).append("isStd", isStd).append("logicalUri", logicalUri).append("theme", theme).append("subtheme", subtheme).append("groupOwn", groupOwn).append("storageInfo", storageInfo).append("physicalUri", physicalUri).append("inputSrc", inputSrc).append("ingestionPipeline", ingestionPipeline).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(isStd).append(subtheme).append(groupOwn).append(logicalUri).append(storageInfo).append(ingestionPipeline).append(inputSrc).append(readType).append(theme).append(additionalProperties).append(datasetType).append(physicalUri).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Operational) == false) {
            return false;
        }
        Operational rhs = ((Operational) other);
        return new EqualsBuilder().append(isStd, rhs.isStd).append(subtheme, rhs.subtheme).append(groupOwn, rhs.groupOwn).append(logicalUri, rhs.logicalUri).append(storageInfo, rhs.storageInfo).append(ingestionPipeline, rhs.ingestionPipeline).append(inputSrc, rhs.inputSrc).append(readType, rhs.readType).append(theme, rhs.theme).append(additionalProperties, rhs.additionalProperties).append(datasetType, rhs.datasetType).append(physicalUri, rhs.physicalUri).isEquals();
    }

}
