// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner;

import ch.postfinance.devops.fusion.demo.apptest.runner.context.ThreadLocalApplicationTestContext;
import ch.postfinance.devops.fusion.demo.apptest.runner.executor.TestExecutorFactory;
import ch.postfinance.devops.fusion.demo.apptest.runner.validation.ValidationException;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestCase;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestStep;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestCaseRunner {

    private static final Log log = LogFactory.getLog(TestCaseRunner.class);

    private TestCaseRunner() {
        // Static access only
    }

    public static TestCaseRunner getInstance() {
        return TestCaseRunnerHolder.INSTANCE;
    }

    public void run(TestCase testCase) throws ValidationException {
        log.info("Running test case: " + testCase.getId() + " - " + testCase.getName());

        new ThreadLocalApplicationTestContext().get().getTestCaseMemento().setTrmId(testCase.getTrmId());

        for (TestStep testStep : testCase.getTestStep()) {
            runTestStep(testCase, testStep);
        }
    }

    private void runTestStep(TestCase testCase, TestStep testStep) throws ValidationException {
        if (log.isDebugEnabled()) {
            if (StringUtils.isNotBlank(testStep.getTestStepId()))
                log.info("Test Step Id: " + testStep.getTestStepId());
            else
                log.info("Test Step No. " + (testCase.getTestStep().indexOf(testStep) + 1) + " (" + testStep.getClass().getSimpleName() + ")");
        }

        TestExecutorFactory.getInstance().getForTestStep(testStep).execute();
    }

    private static class TestCaseRunnerHolder {

        static final TestCaseRunner INSTANCE = getInstance();

        static TestCaseRunner getInstance() {
            return new TestCaseRunner();
        }
    }
}
