
package com.example.notification.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "notification_controller_whitelistrule"
})

public class NotificationControllerWhitelistrule {

    @JsonProperty("notification_controller_whitelistrule")
    private List<NotificationControllerWhitelistrule__1> notificationControllerWhitelistrule = null;

    @JsonProperty("notification_controller_whitelistrule")
    public List<NotificationControllerWhitelistrule__1> getNotificationControllerWhitelistrule() {
        return notificationControllerWhitelistrule;
    }

    @JsonProperty("notification_controller_whitelistrule")
    public void setNotificationControllerWhitelistrule(List<NotificationControllerWhitelistrule__1> notificationControllerWhitelistrule) {
        this.notificationControllerWhitelistrule = notificationControllerWhitelistrule;
    }

}
