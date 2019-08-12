package com.simkiv.tickets.dto.ticketevolution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "barcode",
        "id",
        "seat"
})
public class Ticket {

    @JsonProperty("barcode")
    private String barcode;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("seat")
    private Integer seat;
}
