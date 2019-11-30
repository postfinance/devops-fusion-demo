// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.domain.repository;

import ch.postfinance.devops.fusion.demo.thirt.party.simulator.domain.wrapper.TestDataWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;

public abstract class AbstractTestDataRepository<T, W extends TestDataWrapper<T>> {

    protected final Log log = LogFactory.getLog(getClass());

    private final String resourceLocation;
    private final Class<W> wrapperClass;

    private List<T> testData;
    private T defaultResponse;

    public AbstractTestDataRepository(String resourceLocation, Class<W> wrapperClass) throws JAXBException {
        this.resourceLocation = resourceLocation;
        this.wrapperClass = wrapperClass;

        this.testData = loadTestData();

        if (log.isDebugEnabled()) {
            log.debug("Read input from resource '" + resourceLocation + "', " + getTestData().size() + " pairs found");
        }

        if (log.isTraceEnabled()) {
            log.trace("Repository contains: " + getTestData());
        }

        int indexOfDefaultEntity = getIndexOfDefaultEntity();
        defaultResponse = testData.get(indexOfDefaultEntity);

        if (log.isDebugEnabled()) {
            log.debug("Got default value: '" + defaultResponse + "'");
        }

        testData.remove(indexOfDefaultEntity);
    }

    protected List<T> getTestData() {
        return testData;
    }

    protected T getDefaultResponse() {
        if (log.isDebugEnabled()) {
            log.debug("Returning default response: " + defaultResponse);
        }

        return defaultResponse;
    }

    @SuppressWarnings("unchecked")
    private List<T> loadTestData() throws JAXBException {
        log.info("Initializing..");

        Unmarshaller unmarshaller = JAXBContext.newInstance(wrapperClass).createUnmarshaller();

        InputStream testDataStream = getClass().getClassLoader().getResourceAsStream(resourceLocation);

        return ((W) unmarshaller.unmarshal(testDataStream)).getTestDatas();
    }

    protected abstract int getIndexOfDefaultEntity();
}
