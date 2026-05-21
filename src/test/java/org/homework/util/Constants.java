package org.homework.util;

public class Constants {
    public static final String DATA_FILES_PATH = "src/test/resources/data-files";
//    public static final String PAYMENT_FILE_PATH = System.getProperty("filePath", DATA_FILES_PATH + "/payment/pain_fixed.xml");
    public static final String PAYMENT_FILE_PATH = System.getProperty("filePath", DATA_FILES_PATH + "/payment/pain.xml");
    public static final String ISO_20022_XSD_FILE_PATH = System.getProperty("xsdFilePath", DATA_FILES_PATH + "/schemas/pain.001.001.11.xsd");

    public static String getResourceFilePath(String path) {
        return DATA_FILES_PATH + path;
    }
}
