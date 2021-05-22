
package com.example.notification.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "bl_title",
    "bl_channelid",
    "bl_text",
    "bl_packagename",
    "bl_subtext"
})
@Generated("jsonschema2pojo")
public class NotificationControllerBlacklistrule__1 {

    @JsonProperty("bl_title")
    private String blTitle;
    @JsonProperty("bl_channelid")
    private String blChannelid;
    @JsonProperty("bl_text")
    private String blText;
    @JsonProperty("bl_packagename")
    private String blPackagename;
    @JsonProperty("bl_subtext")
    private String blSubtext;

    @JsonProperty("bl_title")
    public String getBlTitle() {
        return blTitle;
    }

    @JsonProperty("bl_title")
    public void setBlTitle(String blTitle) {
        this.blTitle = blTitle;
    }

    @JsonProperty("bl_channelid")
    public String getBlChannelid() {
        return blChannelid;
    }

    @JsonProperty("bl_channelid")
    public void setBlChannelid(String blChannelid) {
        this.blChannelid = blChannelid;
    }

    @JsonProperty("bl_text")
    public String getBlText() {
        return blText;
    }

    @JsonProperty("bl_text")
    public void setBlText(String blText) {
        this.blText = blText;
    }

    @JsonProperty("bl_packagename")
    public String getBlPackagename() {
        return blPackagename;
    }

    @JsonProperty("bl_packagename")
    public void setBlPackagename(String blPackagename) {
        this.blPackagename = blPackagename;
    }

    @JsonProperty("bl_subtext")
    public String getBlSubtext() {
        return blSubtext;
    }

    @JsonProperty("bl_subtext")
    public void setBlSubtext(String blSubtext) {
        this.blSubtext = blSubtext;
    }

}
