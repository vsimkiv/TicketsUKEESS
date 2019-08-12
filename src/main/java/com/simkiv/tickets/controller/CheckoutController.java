package com.simkiv.tickets.controller;

import com.braintreegateway.*;
import com.simkiv.tickets.service.BraintreeClient;
import com.simkiv.tickets.service.TicketEvolutionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/checkouts")
@Slf4j
public class CheckoutController {
    private BraintreeClient braintreeClient;
    private TicketEvolutionClient ticketEvolutionClient;


    private Transaction.Status[] TRANSACTION_SUCCESS_STATUSES = new Transaction.Status[] {
            Transaction.Status.AUTHORIZED,
            Transaction.Status.AUTHORIZING,
            Transaction.Status.SETTLED,
            Transaction.Status.SETTLEMENT_CONFIRMED,
            Transaction.Status.SETTLEMENT_PENDING,
            Transaction.Status.SETTLING,
            Transaction.Status.SUBMITTED_FOR_SETTLEMENT
    };

    @Autowired
    public CheckoutController(BraintreeClient braintreeClient, TicketEvolutionClient ticketEvolutionClient) {
        this.braintreeClient = braintreeClient;
        this.ticketEvolutionClient = ticketEvolutionClient;
    }

    @GetMapping(value = "/")
    public String checkout(Model model) {
        String clientToken = braintreeClient.generateClientToken();
        model.addAttribute("clientToken", clientToken);

        return "checkouts/new";
    }

    @PostMapping(value = "/")
    public String postForm(@RequestParam("amount") String amount,
                           @RequestParam("payment_method_nonce") String nonce,
                           Model model,
                           final RedirectAttributes redirectAttributes) {

        Result<Transaction> result = braintreeClient.transact(amount, nonce);

        if (result.isSuccess()) {
            return "redirect:checkouts/" + result.getTarget().getId();
        } else if (result.getTransaction() != null) {
            return "redirect:checkouts/" + result.getTarget().getId();
        } else {
            String errorString = "";
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
            }
            redirectAttributes.addFlashAttribute("errorDetails", errorString);
            return "redirect:checkouts";
        }
    }



    @GetMapping(value = "/{transactionId}")
    public String getTransaction(@PathVariable String transactionId, Model model) {
        Transaction transaction;
        CreditCard creditCard;
        Customer customer;

        try {
            transaction = braintreeClient.getGateway().transaction().find(transactionId);
            creditCard = transaction.getCreditCard();
            customer = transaction.getCustomer();
        } catch (Exception e) {
            log.error("Exception in getTransaction method" + e.getMessage());
            return "redirect:/checkouts";
        }

        model.addAttribute("isSuccess", Arrays.asList(TRANSACTION_SUCCESS_STATUSES).contains(transaction.getStatus()));
        model.addAttribute("transaction", transaction);
        model.addAttribute("creditCard", creditCard);
        model.addAttribute("customer", customer);

        return "checkouts/show";
    }
}