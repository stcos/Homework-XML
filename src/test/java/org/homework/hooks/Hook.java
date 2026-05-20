package org.homework.hooks;

import io.cucumber.java.BeforeAll;
import org.homework.util.Constants;
import org.homework.util.XmlUtil;
import org.testng.Assert;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Hook {


    /**
     * Check that file is a valid xml file and that it respects iso 200022 xsd format before executing the tests
     */


    // Hook fails due to the fact that it does not respect schema pain.001.001.11
    // Moved verification to feature so that the tests will execute
//    @BeforeAll
//    public static void checkFile() throws Exception {
//        // Creating XMLUtil object checks if it is a valid XML
//        XmlUtil xmlUtil = new XmlUtil(Constants.SANITY_FILE_PATH);
//        // Throws error if not valid with schema
//        xmlUtil.validateXmlWithSchema(Constants.ISO_20022_XSD_FILE_PATH);
//    }


}
