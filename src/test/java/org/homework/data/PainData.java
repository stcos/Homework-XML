package org.homework.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//TODO: add object for each CdtTrfTxInf object
public class PainData {

    // Debtor amount
    private BigDecimal debtorSum;
    private LocalDate executionDate;
    // Creditor amounts
    private List<BigDecimal> creditorAmounts;
    private List<String> ibans;

    public PainData(BigDecimal debtorSum, LocalDate executionDate,
                    List<BigDecimal> transactionAmounts, List<String> ibans) {
        this.debtorSum = debtorSum;
        this.executionDate = executionDate;
        this.creditorAmounts = transactionAmounts;
        this.ibans = ibans;
    }

    public BigDecimal getDebtorSum() {
        return debtorSum;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public List<BigDecimal> getCreditorAmounts() {
        return creditorAmounts;
    }

    public List<String> getIbans() {
        return ibans;
    }
}
