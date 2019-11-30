// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.filesystem;

import ch.postfinance.devops.fusion.demo.financial.service.api.TestCase;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestSuite;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestFileLoader {

    public static final String TEST_SCHEMA_LOCATION = "schema/TestSchema.xsd";
    private static final Log log = LogFactory.getLog(TestFileLoader.class);

    private TestFileLoader() {
        // Static utility class
    }

    public static List<TestCase> readTestSuiteFileOrFolder(File fileOrFolder) throws NullPointerException {
        List<TestCase> testCaseList = new ArrayList<>();

        if (fileOrFolder.isDirectory() && fileOrFolder.canRead()) {
            for (File fileOrSubfolder : fileOrFolder.listFiles()) {
                testCaseList.addAll(readTestSuiteFileOrFolder(fileOrSubfolder));
            }
        } else if (fileOrFolder.isFile() && fileOrFolder.canRead() && fileOrFolder.getName().endsWith(".xml")) {
            try {
                testCaseList.addAll(readTestSuiteFile(fileOrFolder));
            } catch (FileNotFoundException | JAXBException e) {
                log.error("Failed to read xml file '" + fileOrFolder.getAbsolutePath() + "': " + e.getLocalizedMessage());
            }
        }

        return testCaseList;
    }

    @SuppressWarnings("unchecked")
    private static List<TestCase> readTestSuiteFile(File file) throws FileNotFoundException, JAXBException {
        List<TestCase> testCaseList = new ArrayList<>();

        if (log.isDebugEnabled()) {
            log.debug("Parsing xml file: " + file.getAbsolutePath());
        }

        validate(file);

        JAXBContext jaxbContext = JAXBContext.newInstance(TestSuite.class.getPackage().getName());
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<TestSuite> jaxbElement = (JAXBElement<TestSuite>) unmarshaller.unmarshal(new FileInputStream(file));
        TestSuite testSuite = jaxbElement.getValue();
        if (testSuite != null) {
            testCaseList.addAll(testSuite.getTestCase());
        }

        return testCaseList;
    }

    private static void validate(File file) {
        try {
            validate(new StreamSource(new FileInputStream(file)));
        } catch (SAXException | IOException e) {
            log.error("Validation error in file '" + file.getAbsolutePath() + "': " + e.getLocalizedMessage());

            throw new IllegalArgumentException(e);
        }
    }

    private static void validate(StreamSource streamSource) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(TEST_SCHEMA_LOCATION));
        Schema schema = schemaFactory.newSchema(schemaFile);
        javax.xml.validation.Validator validator = schema.newValidator();
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        validator.validate(streamSource);
    }
}
