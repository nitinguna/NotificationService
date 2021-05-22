
package com.example.notification.model;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "kind",
    "productId",
    "managedProperty"
})
@Generated("jsonschema2pojo")
public class NotificationManagedConfig {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("productId")
    private String productId;
    @JsonProperty("managedProperty")
    private List<ManagedProperty> managedProperty = null;

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonProperty("managedProperty")
    public List<ManagedProperty> getManagedProperty() {
        return managedProperty;
    }

    @JsonProperty("managedProperty")
    public void setManagedProperty(List<ManagedProperty> managedProperty) {
        this.managedProperty = managedProperty;
    }

}
