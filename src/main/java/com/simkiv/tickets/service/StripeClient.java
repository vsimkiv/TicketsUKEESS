package com.simkiv.tickets.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.model.Token;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripeClient {
    @Value("${stripe.keys.secret}")
    private String stripeSecretKey;

    public Charge charge(String customerId, int amount) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String sourceCard = Customer.retrieve(customerId).getDefaultSource();

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("description", "Charge for " + Customer.retrieve(customerId).getEmail());
        chargeParams.put("source", sourceCard);

        return Charge.create(chargeParams);
    }

    public String createSubscription(String customerId, String plan) {
        Stripe.apiKey = stripeSecretKey;
        String id = null;

        Map<String, Object> item = new HashMap<>();
        item.put("plan", plan);

        Map<String, Object> items = new HashMap<>();
        items.put("0", item);

        Map<String, Object> subscriptionParams = new HashMap<>();
        subscriptionParams.put("customer", customerId);
        subscriptionParams.put("items", items);
        try {
            Subscription subscription = Subscription.create(subscriptionParams);
            id = subscription.getId();
        } catch (StripeException e) {
            log.error(e.getMessage() + " Creation of subscription failed");
        }
        return id;
    }

    public void addCard(String customerId, TestCard testCard) throws StripeException {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", testCard.getNumber());
        cardParams.put("exp_month", testCard.getExpMonth());
        cardParams.put("exp_year", testCard.getExpYear());
        cardParams.put("cvc", testCard.getCvc());

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);

        Map<String, Object> source = new HashMap<>();
        source.put("source", token.getId());

        Customer.retrieve(customerId).getSources().create(source);
    }

    @Getter
    private static final class TestCard {
        private String number = "4242424242424242";
        private String expMonth = "12";
        private String expYear = "2023";
        private String cvc = "123";
    }
}
