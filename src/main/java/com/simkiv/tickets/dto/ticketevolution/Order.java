package com.simkiv.tickets.dto.ticketevolution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.simkiv.tickets.dto.Payment;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "balance",
        "client",
        "created_at",
        "id",
        "payments",
        "pending_balance",
})
public class Order {

    @JsonProperty("balance")
    public String balance;

    @JsonProperty("client")
    public Client client;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("id")
    public Long id;

    @JsonProperty("payments")
    public List<Payment> payments = null;

    @JsonProperty("pending_balance")
    public String pendingBalance;

}