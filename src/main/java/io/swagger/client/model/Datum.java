package io.swagger.client.model;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "changeTime",
    "action",
    "company",
    "type",
    "group"
})
@Generated("jsonschema2pojo")
public class Datum {

    @JsonProperty("changeTime")
    private String changeTime;
    @JsonProperty("action")
    private String action;
    @JsonProperty("company")
    private Company__1 company;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("group")
    private Group group;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("changeTime")
    public String getChangeTime() {
        return changeTime;
    }

    @JsonProperty("changeTime")
    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Datum withChangeTime(String changeTime) {
        this.changeTime = changeTime;
        return this;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    public Datum withAction(String action) {
        this.action = action;
        return this;
    }

    @JsonProperty("company")
    public Company__1 getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(Company__1 company) {
        this.company = company;
    }

    public Datum withCompany(Company__1 company) {
        this.company = company;
        return this;
    }

    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    public Datum withType(Type type) {
        this.type = type;
        return this;
    }

    @JsonProperty("group")
    public Group getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(Group group) {
        this.group = group;
    }

    public Datum withGroup(Group group) {
        this.group = group;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Datum withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Datum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("changeTime");
        sb.append('=');
        sb.append(((this.changeTime == null)?"<null>":this.changeTime));
        sb.append(',');
        sb.append("action");
        sb.append('=');
        sb.append(((this.action == null)?"<null>":this.action));
        sb.append(',');
        sb.append("company");
        sb.append('=');
        sb.append(((this.company == null)?"<null>":this.company));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("group");
        sb.append('=');
        sb.append(((this.group == null)?"<null>":this.group));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.changeTime == null)? 0 :this.changeTime.hashCode()));
        result = ((result* 31)+((this.action == null)? 0 :this.action.hashCode()));
        result = ((result* 31)+((this.company == null)? 0 :this.company.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.group == null)? 0 :this.group.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datum) == false) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return (((((((this.changeTime == rhs.changeTime)||((this.changeTime!= null)&&this.changeTime.equals(rhs.changeTime)))&&((this.action == rhs.action)||((this.action!= null)&&this.action.equals(rhs.action))))&&((this.company == rhs.company)||((this.company!= null)&&this.company.equals(rhs.company))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.group == rhs.group)||((this.group!= null)&&this.group.equals(rhs.group))));
    }

}
