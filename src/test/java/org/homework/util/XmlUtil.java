package org.homework.util;

import org.homework.data.CreditorData;
import org.homework.data.PainData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {

    private Document document;
    private XPath xpath;

    // Full project path is needed for execution
    public XmlUtil(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // needed for namespace validation
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        // throw exception for malformed XML
        try {
            this.document = builder.parse(new File(filePath));
        } catch (SAXException e) {
            throw new Exception("File is not valid XML: " + filePath, e);
        }
        this.xpath = XPathFactory.newInstance().newXPath();
    }


    // Returns the initial total amount
    public BigDecimal getDebtorSum() throws Exception {
        String value = getNodeData("//*[local-name()='GrpHdr']/*[local-name()='CtrlSum']");
        return new BigDecimal(value.trim());
    }

    // Returns all transfer amounts
    public List<BigDecimal> getCreditorAmounts() throws Exception {
        NodeList nodes = getNodeListData("//*[local-name()='InstdAmt']");
        List<BigDecimal> amounts = new ArrayList<>();

        for(int i = 0; i < nodes.getLength(); i++) {
            amounts.add(new BigDecimal(nodes.item(i).getTextContent().trim()));
        }
        return amounts;
    }

    // Returns the requested execution date
    public LocalDate getExecutionDate() throws Exception {
        String value = getNodeData("//*[local-name()='ReqdExctnDt']");
        return LocalDate.parse(value.trim());
    }

//    // Returns all IBANs in the document (debtor + all creditors)
//    public List<String> getAllIbans() throws Exception {
//        List<String> ibans = new ArrayList<>();
//        ibans.add(getDebtorIban());
//        ibans.addAll(getCreditorIbans());
//        return ibans;
//    }

    // Return debtor iban
    public String getDebtorIban() throws Exception {
        String iban = getNodeData("//*[local-name()='DbtrAcct']//*[local-name()='IBAN']");
        return iban;
    }

//    // Return creditor ibans
//    public List<String> getCreditorIbans() throws Exception {
//        NodeList nodes = getNodeListData("//*[local-name()='CdtrAcct']//*[local-name()='IBAN']");
//        List<String> ibans = new ArrayList<>();
//        for (int i = 0; i < nodes.getLength(); i++) {
//            ibans.add(nodes.item(i).getTextContent().trim());
//        }
//        return ibans;
//    }

    // Return namespace
    public String getNameSpace() {
        return document.getDocumentElement().getNamespaceURI();
    }

    // Return PainData object of parsed data
    public PainData parsePaymentMessage() throws Exception {
        return new PainData(
                getDebtorSum(),
                getExecutionDate(),
                getDebtorIban(),
                getCreditors()
        );
    }

    public List<CreditorData> getCreditors() throws Exception {
        NodeList nodes = getNodeListData("//*[local-name()='CdtTrfTxInf']");
        List<CreditorData> creditors = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            String iban = xpath.evaluate(".//*[local-name()='IBAN']", node);
            String amount = xpath.evaluate(".//*[local-name()='InstdAmt']", node);
            String currency = xpath.evaluate(".//*[local-name()='InstdAmt']/@Ccy", node);
            creditors.add(new CreditorData(iban, new BigDecimal(amount.trim()), currency));
        }
        return creditors;
    }

    public void validateXmlWithSchema(String xsdSchemaPath) throws IOException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdSchemaPath));
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    private NodeList getNodeListData(String locator) throws XPathExpressionException {
        return (NodeList) xpath.evaluate(locator, document, XPathConstants.NODESET);
    }

    private String getNodeData(String locator) throws XPathExpressionException {
        return xpath.evaluate(locator, document);
    }

}
