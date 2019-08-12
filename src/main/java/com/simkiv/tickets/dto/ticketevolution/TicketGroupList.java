package com.simkiv.tickets.dto.ticketevolution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total_entries",
        "ticket_groups"
})
public class TicketGroupList {
    @JsonProperty("total_entries")
    public Long totalEntries;
    @JsonProperty("ticket_groups")
    public List<TicketGroup> ticketGroups = null;

}
