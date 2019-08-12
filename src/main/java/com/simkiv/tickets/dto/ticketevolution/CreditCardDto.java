package com.simkiv.tickets.dto.ticketevolution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "expiration_year",
        "last_digits",
        "expiration_month",
        "id",
        "card_company"
})
public class CreditCardDto {

    @JsonProperty("expiration_year")
    public Long expirationYear;

    @JsonProperty("last_digits")
    public String lastDigits;

    @JsonProperty("expiration_month")
    public Long expirationMonth;

    @JsonProperty("id")
    public Long id;

    @JsonProperty("card_company")
    public String cardCompany;
}