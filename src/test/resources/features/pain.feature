Feature: Payment XML file
  Tests validate the information in payment xml file

  Background: Load payment file before each scenario
    Given I load payment file

  Scenario: Validate IBANs
    Then I check if all IBANs in the file are valid

  Scenario: Check amount has 2 digits
    Then I check if the debtor amount has 2 digits

  Scenario: Check date of transaction is in past
    Then I check transaction date is the past

  Scenario: Check payments sum equal to ctrl sum
    Then I check debtor amount equals to credit amounts sum

  Scenario: Check iso 20022 namespace
    Then I check if file is valid ISO 20022 namespace

  Scenario: Check xml file respects ISO 20022 xsd schema
    Then I check that the file respects the xsd schema

  Scenario: Check currencies are the same for all creditors
    Then I check if all creditor have the same currency