package com.simkiv.tickets.controller;

import com.simkiv.tickets.dto.ticketevolution.TicketGroup;
import com.simkiv.tickets.dto.ticketevolution.TicketGroupList;
import com.simkiv.tickets.service.TicketEvolutionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ticket_groups")
public class TicketGroupController {
    private TicketEvolutionClient ticketEvolutionClient;

    @Autowired
    public TicketGroupController(TicketEvolutionClient ticketEvolutionClient) {
        this.ticketEvolutionClient = ticketEvolutionClient;
    }

    @GetMapping("/")
    public TicketGroupList showTicketGroupList(@RequestParam String eventId) {
        return ticketEvolutionClient.showTicketGroupList(eventId);
    }

    @GetMapping("/{id}")
    public TicketGroup showTicketGroup(@PathVariable("id") String ticketGroupId) {
        return ticketEvolutionClient.showTicketGroup(ticketGroupId);
    }
}
