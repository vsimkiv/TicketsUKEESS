package com.simkiv.tickets.controller;

import com.simkiv.tickets.dto.ticketevolution.Ticket;
import com.simkiv.tickets.service.TicketEvolutionClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private TicketEvolutionClient ticketEvolutionClient;

    @GetMapping("/{id}")
    public Ticket showTicket(@PathVariable("id") String ticketId) {
        return ticketEvolutionClient.showTicket(ticketId);
    }
}
