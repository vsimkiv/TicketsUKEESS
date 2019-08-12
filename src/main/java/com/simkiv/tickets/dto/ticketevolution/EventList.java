package com.simkiv.tickets.dto.ticketevolution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "current_page",
        "per_page",
        "events"
})
public class EventList {

    @JsonProperty("current_page")
    public Long currentPage;
    @JsonProperty("per_page")
    public Long perPage;
    @JsonProperty("events")
    public List<Event> events = null;

}