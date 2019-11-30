// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class ApplicationTestContext {

    private static final Log log = LogFactory.getLog(ApplicationTestContext.class);

    private static final String FINANCIAL_AUTHORIZATION_SERVICE_URL_PROPERTY = "runner.endpoints.financial.service.authorizationUrl";

    private final TestCaseMemento testCaseMemento;
    private final PropertiesPropertySource propertiesPropertySource;

    ApplicationTestContext() {
        testCaseMemento = new TestCaseMemento();
        propertiesPropertySource = loadTestProperties();
    }

    private PropertiesPropertySource loadTestProperties() {
        Properties properties;

        try {
            ClassPathResource classPathResource = new ClassPathResource("config/application-test.properties");
            log.info("Loading properties from file " + classPathResource.getFile().getAbsolutePath());
            properties = PropertiesLoaderUtils.loadProperties(classPathResource);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("Properties laoded: " + properties);
        }

        return new PropertiesPropertySource("application-test.properties", properties);
    }

    public TestCaseMemento getTestCaseMemento() {
        return testCaseMemento;
    }

    public Object getFinancialAuthorizationServiceEndpoint() {
        return propertiesPropertySource.getProperty(FINANCIAL_AUTHORIZATION_SERVICE_URL_PROPERTY);
    }
}
