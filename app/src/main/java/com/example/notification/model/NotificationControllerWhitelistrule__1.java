
package com.example.notification.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wl_packagename",
    "wl_subtext",
    "wl_title",
    "wl_text",
    "wl_channelid"
})
@Generated("jsonschema2pojo")
public class NotificationControllerWhitelistrule__1 {

    @JsonProperty("wl_packagename")
    private String wlPackagename;
    @JsonProperty("wl_subtext")
    private String wlSubtext;
    @JsonProperty("wl_title")
    private String wlTitle;
    @JsonProperty("wl_text")
    private String wlText;
    @JsonProperty("wl_channelid")
    private String wlChannelid;

    @JsonProperty("wl_packagename")
    public String getWlPackagename() {
        return wlPackagename;
    }

    @JsonProperty("wl_packagename")
    public void setWlPackagename(String wlPackagename) {
        this.wlPackagename = wlPackagename;
    }

    @JsonProperty("wl_subtext")
    public String getWlSubtext() {
        return wlSubtext;
    }

    @JsonProperty("wl_subtext")
    public void setWlSubtext(String wlSubtext) {
        this.wlSubtext = wlSubtext;
    }

    @JsonProperty("wl_title")
    public String getWlTitle() {
        return wlTitle;
    }

    @JsonProperty("wl_title")
    public void setWlTitle(String wlTitle) {
        this.wlTitle = wlTitle;
    }

    @JsonProperty("wl_text")
    public String getWlText() {
        return wlText;
    }

    @JsonProperty("wl_text")
    public void setWlText(String wlText) {
        this.wlText = wlText;
    }

    @JsonProperty("wl_channelid")
    public String getWlChannelid() {
        return wlChannelid;
    }

    @JsonProperty("wl_channelid")
    public void setWlChannelid(String wlChannelid) {
        this.wlChannelid = wlChannelid;
    }

}
