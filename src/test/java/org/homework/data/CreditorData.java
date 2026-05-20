package org.homework.data;

import java.math.BigDecimal;

public class CreditorData {
    private String iban;
    private BigDecimal amount;
    private String currency;

    public CreditorData(String iban, BigDecimal amount, String currency) {
        this.iban = iban;
        this.amount = amount;
        this.currency = currency;
    }

    public String getIban() { return iban; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
}
