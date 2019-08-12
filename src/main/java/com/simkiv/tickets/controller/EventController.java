package com.simkiv.tickets.controller;

import com.simkiv.tickets.dto.ticketevolution.Event;
import com.simkiv.tickets.dto.ticketevolution.EventList;
import com.simkiv.tickets.service.TicketEvolutionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private TicketEvolutionClient ticketEvolutionClient;

    @Autowired
    public EventController(TicketEvolutionClient ticketEvolutionClient) {
        this.ticketEvolutionClient = ticketEvolutionClient;
    }

    @GetMapping
    public EventList listEvents() {
        return ticketEvolutionClient.eventList();
    }

    @GetMapping("/{id}")
    public Event showEvent(@PathVariable("id") String eventId) {
        return ticketEvolutionClient.showEvent(eventId);
    }

}
