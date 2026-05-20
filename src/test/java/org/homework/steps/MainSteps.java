package org.homework.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.homework.data.CreditorData;
import org.homework.data.PainData;
import org.homework.util.Constants;
import org.homework.util.XmlUtil;
import org.testng.Assert;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainSteps {

    private PainData painData;
    private XmlUtil xmlUtil;

    // SETUP STEPS
    @Given("I load payment file")
    public void loadPaymentFile() throws Exception {
        xmlUtil = new XmlUtil(Constants.PAYMENT_FILE_PATH);
        painData = xmlUtil.parsePaymentMessage();
    }
    @Given("I load payment file {string}")
    public void loadPaymentFile(String filePath) throws Exception {
        xmlUtil = new XmlUtil(filePath);
        painData = xmlUtil.parsePaymentMessage();
    }

    // TEST STEPS
    @Then("I check if file is valid ISO 20022 namespace")
    public void checkValidISO20022Namespace() {
        String namespace = xmlUtil.getNameSpace();
        boolean isISO20022 = namespace.startsWith("urn:iso:std:iso:20022");
        Assert.assertTrue(isISO20022, "The XML file is not the correct format");
    }

    /**
     * Checks that the debtor total amount (CtrlSum) has at least 2 digits
     */
    @Then("I check if the debtor amount has 2 digits")
    public void checkDebtorAmountHasDigits() {
        BigDecimal amount = painData.getDebtorSum();
        boolean hasDecimal = amount.scale() > 0;
        boolean hasTwoIntegerDigits = amount.toBigInteger().toString().length() >= 2;
        Assert.assertTrue(hasDecimal || hasTwoIntegerDigits);
    }

    /**
     * Check that all amounts in file hs at least 2 digits
     */
    @Then("I check if the creditor amounts have 2 digits")
    public void checkCreditAmountsHaveDigits() {
        BigDecimal amount = painData.getDebtorSum();
        System.out.println(amount.scale());
        boolean hasDecimal = amount.scale() > 0;
        boolean hasTwoIntegerDigits = amount.toBigInteger().toString().length() >= 2;
        Assert.assertTrue(hasDecimal || hasTwoIntegerDigits);
    }


    /**
     * Check that the debtor amount equals the sum of all creditor amounts.
     */
    @Then("I check debtor amount equals to credit amounts sum")
    public void debtorAmountEqualsCreditorAmountsSum() {
        BigDecimal ctrlSum = painData.getDebtorSum();
        List<BigDecimal> amounts = painData.getCreditorData().stream().map(CreditorData::getAmount).toList();
        BigDecimal sum = amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        Assert.assertEquals(ctrlSum, sum);
    }


    @Then("I check if all IBANs in the file are valid")
    public void allIbansAreValid() throws Exception {
        List<String> allIbans = new ArrayList<>();
        allIbans.add(painData.getDebtorIban());
        allIbans.addAll(painData.getCreditorData().stream().map(CreditorData::getIban).toList());
        for (String iban : allIbans) {
            if (!isValidIban(iban)) {
                throw new Exception("Invalid IBAN: " + iban);
            }
        }
    }

    /**
     * Checks that the execution date is not in the future
     */
    @Then("I check transaction date is the past")
    public void checkDateIsNotInFuture() {
        Assert.assertTrue(painData.getExecutionDate().isBefore(LocalDate.now()));
    }

    /**
     * Check payment file respects xsd format
     */
    @Then("I check that the file respects the xsd schema")
    public void validateXmlWithSchema() throws IOException, SAXException {
        xmlUtil.validateXmlWithSchema(Constants.ISO_20022_XSD_FILE_PATH);
    }

    @Then("I check if all creditor have the same currency")
    public void checkCurrencyEqualsToAll() {
        List<String> creditorCurrencies = painData.getCreditorData().stream().map(CreditorData::getCurrency).toList();
        boolean allSameCurrency = creditorCurrencies.stream().distinct().count() == 1;
        Assert.assertTrue(allSameCurrency, "Not all currencies have the same value");
    }

    private boolean isValidIban(String iban) {
        iban = iban.replaceAll("\\s+", "");
        iban = iban.toUpperCase();

        if (iban.length() < 4) {
            return false;
        }

        iban = iban.substring(4) + iban.substring(0,4);

        // convert letters to digits
        String total = "";
        for (int i = 0; i < iban.length(); i++) {

            int charValue = Character.getNumericValue(iban.charAt(i));

            if (charValue < 0 || charValue > 35) {
                return false;
            }
            total += charValue;
        }

        BigInteger ibanNumber = new BigInteger(total);
        return ibanNumber.mod(BigInteger.valueOf(97)).intValue() == 1;
    }

}
