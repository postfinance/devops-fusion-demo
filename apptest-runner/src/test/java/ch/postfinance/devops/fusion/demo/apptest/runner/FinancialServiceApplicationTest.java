// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner;

import ch.postfinance.devops.fusion.demo.apptest.runner.filesystem.ApplicationTestsFolder;
import ch.postfinance.devops.fusion.demo.apptest.runner.filesystem.TestFileLoader;
import ch.postfinance.devops.fusion.demo.apptest.runner.util.ApplicationTestUtil;
import ch.postfinance.devops.fusion.demo.apptest.runner.validation.ValidationException;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FinancialServiceApplicationTest {

    private static final Log log = LogFactory.getLog(FinancialServiceApplicationTest.class);

    public static Stream<TestCase> loadTestCases() throws IllegalArgumentException, NullPointerException {
        List<TestCase> testCases = new ArrayList<>();

        for (File fileOrFolder : new ApplicationTestsFolder().listFiles()) {
            testCases.addAll(TestFileLoader.readTestSuiteFileOrFolder(fileOrFolder));
        }

        for (TestCase testCase : testCases) {
            ApplicationTestUtil.copyInheritedData(testCase);
        }

        return testCases.parallelStream();
    }

    @MethodSource("loadTestCases")
    @ParameterizedTest(name = "Test case: {0}")
    public void applicationTest(TestCase testCase) throws Exception {
        Assumptions.assumeTrue(testCase.isEnabled(), "Test '" + testCase + "' is disabled!");

        try {
            TestCaseRunner.getInstance().run(testCase);
        } catch (Throwable throwable) {
            final String localizedMessage = throwable.getLocalizedMessage();
            log.error("Test failed: " + localizedMessage, throwable);

            if (throwable instanceof ValidationException) {
                Assertions.fail(testCase.getId() + " - " + testCase.getName() + ": " + localizedMessage);
            } else {
                throw throwable;
            }
        }
    }
}
