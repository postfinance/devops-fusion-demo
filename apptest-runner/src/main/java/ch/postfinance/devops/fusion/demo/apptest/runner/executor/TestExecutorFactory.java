// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.executor;

import ch.postfinance.devops.fusion.demo.financial.service.api.TestStep;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;

public class TestExecutorFactory {

    private static final Log log = LogFactory.getLog(TestExecutorFactory.class);

    private static final char STRING_PACKAGE_SEPARATOR = '.';
    private static final String TEST_EXECUTOR_SUFFIX = "TestExecutor";

    private TestExecutorFactory() {
        // Static access only
    }

    public static TestExecutorFactory getInstance() {
        return TestExecutorFactoryHolder.INSTANCE;
    }

    public AbstractTestExecutor<?> getForTestStep(TestStep testStep) {
        if (log.isDebugEnabled()) {
            log.debug("Looking for executor matching " + testStep.getClass());
        }

        try {
            Class<?> executorClass = Class.forName(constructExecutorNameFromTestStep(testStep));
            return (AbstractTestExecutor<?>) executorClass.getConstructor(TestStep.class).newInstance(testStep);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot find executor for test " + testStep.getClass(), e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("Error creating executor for test " + testStep.getClass(), e);
        }
    }

    private String constructExecutorNameFromTestStep(TestStep testStep) {
        String simpleName = testStep.getClass().getSimpleName();

        if (!simpleName.contains("Call")) {
            throw new IllegalArgumentException("Cannot construct executor without a callee!");
        }

        simpleName = simpleName.replaceFirst("Call$", "");

        String executorFullyQualifiedName = TestExecutorFactory.class.getPackageName() + STRING_PACKAGE_SEPARATOR + simpleName + TEST_EXECUTOR_SUFFIX;

        log.debug("Constructed executors fully qualified name: " + executorFullyQualifiedName);

        return executorFullyQualifiedName;
    }

    private static class TestExecutorFactoryHolder {

        static final TestExecutorFactory INSTANCE = getInstance();

        static TestExecutorFactory getInstance() {
            return new TestExecutorFactory();
        }
    }
}
