package com.simkiv.tickets.service;

import com.braintreegateway.*;
import com.simkiv.tickets.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BraintreeClient {

    private String merchantId = "4q9x9rsnm7bzmst8";
    private String publicKey = "rp5f9tyjddrx5ccy";
    private String privateKey = "74b77f8a50c0ace5f5d0d9b0c9b68967";

    private BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            merchantId,
            publicKey,
            privateKey
    );

    public BraintreeGateway getGateway() {
        return gateway;
    }

    public String generateClientToken() {
        return gateway.clientToken().generate();
    }

    public Result<Customer> createCustomer(User user) {
        CustomerRequest request = new CustomerRequest()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail());
        Result<Customer> result = gateway.customer().create(request);
        return result;
    }

    public Result<Transaction> transact(String amount, String nonce) {
        BigDecimal decimalAmount = new BigDecimal(amount);

        TransactionRequest request = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(nonce)
                .options()
                .submitForSettlement(true)
                .done();

        return gateway.transaction().sale(request);
    }
}
