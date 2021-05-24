
package com.example.notification.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;



@JsonInclude(JsonInclude.Include.NON_NULL)


public class ManagedProperty {

    @JsonProperty("notificationRuleType")
    private String notificationRuleType;
    @JsonProperty("notification_controller_blacklistrules")
    private List<NotificationControllerBlacklistrule> notificationControllerBlacklistrules = null;
    @JsonProperty("notification_controller_whitelistrules")
    private List<NotificationControllerWhitelistrule> notificationControllerWhitelistrules = null;
    @JsonProperty("notificationIsControlled")
    private Boolean notificationIsControlled;

    @JsonProperty("notificationRuleType")
    public String getNotificationRuleType() {
        return notificationRuleType;
    }

    @JsonProperty("notificationRuleType")
    public void setNotificationRuleType(String notificationRuleType) {
        this.notificationRuleType = notificationRuleType;
    }

    @JsonProperty("notification_controller_blacklistrules")
    public List<NotificationControllerBlacklistrule> getNotificationControllerBlacklistrules() {
        return notificationControllerBlacklistrules;
    }

    @JsonProperty("notification_controller_blacklistrules")
    public void setNotificationControllerBlacklistrules(List<NotificationControllerBlacklistrule> notificationControllerBlacklistrules) {
        this.notificationControllerBlacklistrules = notificationControllerBlacklistrules;
    }

    @JsonProperty("notification_controller_whitelistrules")
    public List<NotificationControllerWhitelistrule> getNotificationControllerWhitelistrules() {
        return notificationControllerWhitelistrules;
    }

    @JsonProperty("notification_controller_whitelistrules")
    public void setNotificationControllerWhitelistrules(List<NotificationControllerWhitelistrule> notificationControllerWhitelistrules) {
        this.notificationControllerWhitelistrules = notificationControllerWhitelistrules;
    }

    @JsonProperty("notificationIsControlled")
    public Boolean getNotificationIsControlled() {
        return notificationIsControlled;
    }

    @JsonProperty("notificationIsControlled")
    public void setNotificationIsControlled(Boolean notificationIsControlled) {
        this.notificationIsControlled = notificationIsControlled;
    }

}
