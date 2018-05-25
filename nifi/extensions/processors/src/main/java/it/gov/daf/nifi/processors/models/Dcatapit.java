
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
    "name",
    "notes",
    "relationships_as_object",
    "holder_identifier",
    "identifier",
    "tags",
    "groups",
    "modified",
    "alternate_identifier",
    "relationships_as_subject",
    "holder_name",
    "publisher_identifier",
    "resources",
    "frequency",
    "title",
    "owner_org",
    "theme",
    "publisher_name"
})
public class Dcatapit {

    /**
     * The Name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    private String name = "";
    /**
     * The Notes Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("notes")
    private String notes = "";
    @JsonProperty("relationships_as_object")
    private List<Object> relationshipsAsObject = new ArrayList<Object>();
    /**
     * The Holder_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("holder_identifier")
    private String holderIdentifier = "";
    /**
     * The Identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("identifier")
    private String identifier = "";
    @JsonProperty("tags")
    private List<Tag> tags = new ArrayList<Tag>();
    @JsonProperty("groups")
    private List<Object> groups = new ArrayList<Object>();
    /**
     * The Modified Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("modified")
    private String modified = "";
    /**
     * The Alternate_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("alternate_identifier")
    private String alternateIdentifier = "";
    @JsonProperty("relationships_as_subject")
    private List<Object> relationshipsAsSubject = new ArrayList<Object>();
    /**
     * The Holder_name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("holder_name")
    private String holderName = "";
    /**
     * The Publisher_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("publisher_identifier")
    private String publisherIdentifier = "";
    @JsonProperty("resources")
    private List<Object> resources = new ArrayList<Object>();
    /**
     * The Frequency Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("frequency")
    private String frequency = "";
    /**
     * The Title Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("title")
    private String title = "";
    /**
     * The Owner_org Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("owner_org")
    private String ownerOrg = "";
    /**
     * The Theme Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("theme")
    private String theme = "";
    /**
     * The Publisher_name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("publisher_name")
    private String publisherName = "";
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The Name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The Name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The Notes Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    /**
     * The Notes Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("relationships_as_object")
    public List<Object> getRelationshipsAsObject() {
        return relationshipsAsObject;
    }

    @JsonProperty("relationships_as_object")
    public void setRelationshipsAsObject(List<Object> relationshipsAsObject) {
        this.relationshipsAsObject = relationshipsAsObject;
    }

    /**
     * The Holder_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("holder_identifier")
    public String getHolderIdentifier() {
        return holderIdentifier;
    }

    /**
     * The Holder_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("holder_identifier")
    public void setHolderIdentifier(String holderIdentifier) {
        this.holderIdentifier = holderIdentifier;
    }

    /**
     * The Identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * The Identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("tags")
    public List<Tag> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @JsonProperty("groups")
    public List<Object> getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    /**
     * The Modified Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    /**
     * The Modified Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     * The Alternate_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("alternate_identifier")
    public String getAlternateIdentifier() {
        return alternateIdentifier;
    }

    /**
     * The Alternate_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("alternate_identifier")
    public void setAlternateIdentifier(String alternateIdentifier) {
        this.alternateIdentifier = alternateIdentifier;
    }

    @JsonProperty("relationships_as_subject")
    public List<Object> getRelationshipsAsSubject() {
        return relationshipsAsSubject;
    }

    @JsonProperty("relationships_as_subject")
    public void setRelationshipsAsSubject(List<Object> relationshipsAsSubject) {
        this.relationshipsAsSubject = relationshipsAsSubject;
    }

    /**
     * The Holder_name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("holder_name")
    public String getHolderName() {
        return holderName;
    }

    /**
     * The Holder_name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("holder_name")
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    /**
     * The Publisher_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("publisher_identifier")
    public String getPublisherIdentifier() {
        return publisherIdentifier;
    }

    /**
     * The Publisher_identifier Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("publisher_identifier")
    public void setPublisherIdentifier(String publisherIdentifier) {
        this.publisherIdentifier = publisherIdentifier;
    }

    @JsonProperty("resources")
    public List<Object> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<Object> resources) {
        this.resources = resources;
    }

    /**
     * The Frequency Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("frequency")
    public String getFrequency() {
        return frequency;
    }

    /**
     * The Frequency Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("frequency")
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * The Title Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * The Title Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The Owner_org Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("owner_org")
    public String getOwnerOrg() {
        return ownerOrg;
    }

    /**
     * The Owner_org Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("owner_org")
    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
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
     * The Publisher_name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("publisher_name")
    public String getPublisherName() {
        return publisherName;
    }

    /**
     * The Publisher_name Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("publisher_name")
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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
        return new ToStringBuilder(this).append("name", name).append("notes", notes).append("relationshipsAsObject", relationshipsAsObject).append("holderIdentifier", holderIdentifier).append("identifier", identifier).append("tags", tags).append("groups", groups).append("modified", modified).append("alternateIdentifier", alternateIdentifier).append("relationshipsAsSubject", relationshipsAsSubject).append("holderName", holderName).append("publisherIdentifier", publisherIdentifier).append("resources", resources).append("frequency", frequency).append("title", title).append("ownerOrg", ownerOrg).append("theme", theme).append("publisherName", publisherName).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(relationshipsAsObject).append(identifier).append(holderName).append(notes).append(relationshipsAsSubject).append(groups).append(resources).append(title).append(tags).append(publisherIdentifier).append(frequency).append(ownerOrg).append(publisherName).append(name).append(holderIdentifier).append(modified).append(alternateIdentifier).append(theme).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Dcatapit) == false) {
            return false;
        }
        Dcatapit rhs = ((Dcatapit) other);
        return new EqualsBuilder().append(relationshipsAsObject, rhs.relationshipsAsObject).append(identifier, rhs.identifier).append(holderName, rhs.holderName).append(notes, rhs.notes).append(relationshipsAsSubject, rhs.relationshipsAsSubject).append(groups, rhs.groups).append(resources, rhs.resources).append(title, rhs.title).append(tags, rhs.tags).append(publisherIdentifier, rhs.publisherIdentifier).append(frequency, rhs.frequency).append(ownerOrg, rhs.ownerOrg).append(publisherName, rhs.publisherName).append(name, rhs.name).append(holderIdentifier, rhs.holderIdentifier).append(modified, rhs.modified).append(alternateIdentifier, rhs.alternateIdentifier).append(theme, rhs.theme).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
