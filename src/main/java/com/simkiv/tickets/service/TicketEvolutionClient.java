package com.simkiv.tickets.service;

import com.simkiv.tickets.dto.ticketevolution.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class TicketEvolutionClient {
    private RestTemplate restTemplate = new RestTemplate();

    // for test
    private String eventId = "1667984";
    private String ticketGroupId = "576436221";


    public EventList eventList() {
        String url = "api.sandbox.ticketevolution.com/v9/events?page=1&per_page=10";
        EventList response = null;
        try {
            response = restTemplate.getForObject(new URI(url), EventList.class);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return response;
    }

    public Event showEvent(String eventId) {

        String url = "api.sandbox.ticketevolution.com/v9/events/" + eventId;
        Event response = null;
        try {
            response = restTemplate.getForObject(new URI(url), Event.class);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return response;
    }


    public TicketGroupList showTicketGroupList(String eventId) {

        String url = "api.sandbox.ticketevolution.com/v9/ticket_groups?event_id=" + eventId;
        TicketGroupList response = null;
        try {
            response = restTemplate.getForObject(new URI(url), TicketGroupList.class);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return response;
    }


    public TicketGroup showTicketGroup(String ticketGroupId) {

        String url = "api.sandbox.ticketevolution.com/v9/ticket-groups/" + ticketGroupId;
        TicketGroup response = null;
        try {
            response = restTemplate.getForObject(new URI(url), TicketGroup.class);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return response;
    }

    public Ticket showTicket(String ticketId) {

        String url = "api.sandbox.ticketevolution.com/v9/tickets/" + ticketId;
        Ticket response = null;
        try {
            response = restTemplate.getForObject(new URI(url), Ticket.class);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
        return response;
    }

}