// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.validation;

public class ValidationException extends Exception {

    private final String field;
    private final Object expected;
    private final Object actual;

    public ValidationException(String field, Object expected, Object actual) {
        this.field = field;
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public String getLocalizedMessage() {
        return "Assert on " + field + " failed: Expected '" + expected + "' but was '" + actual + "'!";
    }
}
