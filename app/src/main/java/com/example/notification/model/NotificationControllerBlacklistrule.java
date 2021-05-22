
package com.example.notification.model;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "notification_controller_blacklistrule"
})
@Generated("jsonschema2pojo")
public class NotificationControllerBlacklistrule {

    @JsonProperty("notification_controller_blacklistrule")
    private List<NotificationControllerBlacklistrule__1> notificationControllerBlacklistrule = null;

    @JsonProperty("notification_controller_blacklistrule")
    public List<NotificationControllerBlacklistrule__1> getNotificationControllerBlacklistrule() {
        return notificationControllerBlacklistrule;
    }

    @JsonProperty("notification_controller_blacklistrule")
    public void setNotificationControllerBlacklistrule(List<NotificationControllerBlacklistrule__1> notificationControllerBlacklistrule) {
        this.notificationControllerBlacklistrule = notificationControllerBlacklistrule;
    }

}
