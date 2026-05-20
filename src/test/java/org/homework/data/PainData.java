package org.homework.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PainData {

    // Debtor amount
    private BigDecimal debtorSum;
    private LocalDate executionDate;
    // Creditor amounts
    private String debtorIban;
    private List<CreditorData> creditors;

    public PainData(BigDecimal debtorSum, LocalDate executionDate, String debtorIban,
                    List<CreditorData> creditors) {
        this.debtorSum = debtorSum;
        this.executionDate = executionDate;
        this.debtorIban = debtorIban;
        this.creditors = creditors;
    }

    public BigDecimal getDebtorSum() {
        return debtorSum;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public List<CreditorData> getCreditorData() {
        return creditors;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

}
