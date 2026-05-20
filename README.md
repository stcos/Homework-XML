# Homework XML
A Cucumber + TestNG project that validates an ISO 20022 PAIN XML payment file.

## Requirements

- Java 23
- Maven

## Running the tests
### From command line
```
mvn install -DskipTests
mvn test
```

### From IntelliJ
```
Execute TestRunner class
```
files that are verified in tests are in src/test/resources/data-files
Path for files that are verified are placed in org.homework.util.Constants


To specify a custom file path or/and different schema location

(current schema downloaded from https://www.iso20022.org/catalogue-messages/iso-20022-messages-archive?search=001.001.11):

Message name: CustomerCreditTransferInitiationV11

```
mvn test -DfilePath="src/test/resources/data-files/sanity/pain.xml" 
mvn test -DxsdFilePath="src/test/resources/data-files/schemas/pain.001.001.11.xsd"
mvn test -DfilePath="src/test/resources/data-files/sanity/pain.xml" -DxsdFilePath="src/test/resources/data-files/schemas/pain.001.001.11.xsd"
```

## What is executed
src/test/java/resources/features/sanity.feature tests


