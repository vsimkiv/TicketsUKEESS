package com.simkiv.tickets.dto.ticketevolution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "type",
        "row",
        "section",
        "quantity",
        "available_quantity",
        "wholesale_price",
        "eticket",
        "retail_price"
})
public class TicketGroup {

    @JsonProperty("id")
    public Long id;

    @JsonProperty("type")
    public String type;

    @JsonProperty("row")
    public String row;

    @JsonProperty("section")
    public String section;

    @JsonProperty("quantity")
    public Integer quantity;
    @JsonProperty("available_quantity")
    public Integer availableQuantity;

    @JsonProperty("wholesale_price")
    public BigDecimal wholesalePrice;

    @JsonProperty("eticket")
    public Boolean eticket;

    @JsonProperty("retail_price")
    public BigDecimal retailPrice;
}