// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.util;

import ch.postfinance.devops.fusion.demo.financial.service.api.Data;
import ch.postfinance.devops.fusion.demo.financial.service.api.DataContainingTestStep;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestCase;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestStep;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApplicationTestUtil {

    private static final Log log = LogFactory.getLog(ApplicationTestUtil.class);

    private static final String GETTER_METHOD_PREFIX = "get";
    private static final String SETTER_METHOD_PREFIX = "set";

    private ApplicationTestUtil() {
        // Static access only
    }

    public static void copyInheritedData(TestCase testCase) {
        if (log.isDebugEnabled()) {
            log.debug("Copying inherited data from test case " + testCase);
        }

        Map<String, Method> testCaseGetMethods = getPrefixedMethodsByShortName(testCase.getClass().getDeclaredMethods(), GETTER_METHOD_PREFIX);

        for (TestStep testStep : testCase.getTestStep()) {
            copyDataFromTestCase(testCase, testCaseGetMethods, testStep);
        }
    }

    private static Map<String, Method> getPrefixedMethodsByShortName(Method[] classDeclaredMethods, String prefix) {
        return getPrefixedMethods(classDeclaredMethods, prefix).stream()
                .collect(Collectors.toMap(method -> StringUtils.substring(method.getName(), prefix.length()), Function.identity()));
    }

    private static List<Method> getPrefixedMethods(Method[] classDeclaredMethods, String prefix) {
        return Arrays.stream(classDeclaredMethods)
                .filter(method -> method.getName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    private static void copyDataFromTestCase(TestCase fromTestCase, Map<String, Method> testCaseGetMethods, TestStep toTestStep) {
        if (!(toTestStep instanceof DataContainingTestStep)) {
            return;
        }

        if (log.isTraceEnabled()) {
            log.trace("Copying data into test step " + toTestStep);
        }

        Data testStepData = DataContainingTestStep.class.cast(toTestStep).getData();

        List<Method> testStepSetMethods = getPrefixedMethods(testStepData.getClass().getDeclaredMethods(), SETTER_METHOD_PREFIX);

        for (Method testStepSetMethod : testStepSetMethods) {
            copyDataFromTestCaseIfNoValuePresent(fromTestCase, testCaseGetMethods, testStepData, testStepSetMethod);
        }
    }

    private static void copyDataFromTestCaseIfNoValuePresent(TestCase fromTestCase, Map<String, Method> testCaseGetMethods, Data toTestStepData, Method testStepSetMethod) {
        String shortName = testStepSetMethod.getName().substring(3);

        if (!testCaseGetMethods.containsKey(shortName)) {
            return;
        }

        Map<String, Method> testStepDataGetMethods = getPrefixedMethodsByShortName(toTestStepData.getClass().getDeclaredMethods(), GETTER_METHOD_PREFIX);

        if (testStepDataGetMethods.containsKey(shortName) && isTestStepDataAlreadyPresent(toTestStepData, testStepDataGetMethods.get(shortName))) {
            return;
        }

        try {
            copyNonNullValuesFromTestCase(fromTestCase, testCaseGetMethods.get(shortName), toTestStepData, testStepSetMethod);
        } catch (Exception e) {
            log.warn("Writing inherited data did not work. Trying next one: " + e.getLocalizedMessage());
        }
    }

    private static void copyNonNullValuesFromTestCase(TestCase fromTestCase, Method testCaseGetMethod, Data toTestStepData, Method testStepSetMethod) throws IllegalAccessException, InvocationTargetException {
        Object value = testCaseGetMethod.invoke(fromTestCase);

        if (value == null) {
            return;
        }

        testStepSetMethod.invoke(toTestStepData, value);

        if (log.isDebugEnabled()) {
            log.debug(
                    "Call method: '" + testStepSetMethod.getName() + "', value: '" + value + "', class: '" + toTestStepData.getClass().getSimpleName() + "', id: '" + fromTestCase.getId()
                            + "'");
        }
    }

    private static boolean isTestStepDataAlreadyPresent(Data data, Method testStepGetMethod) {
        try {
            Object testStepGetMethodValue = testStepGetMethod.invoke(data);

            if (log.isTraceEnabled() && testStepGetMethodValue != null) {
                log.warn("Value already assigned to test step: " + testStepGetMethod.getName() + " - value: " + testStepGetMethodValue);
            }

            if (testStepGetMethodValue != null) {
                return true;
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.warn("Could not invoke get method: " + e.getLocalizedMessage());
        }

        return false;
    }
}
