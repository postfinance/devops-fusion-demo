// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.executor;

import ch.postfinance.devops.fusion.demo.apptest.runner.validation.ValidationException;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestStep;

public abstract class AbstractTestExecutor<T extends TestStep> {

    private final T concreteTestStep;

    public AbstractTestExecutor(TestStep testStep) {
        this.concreteTestStep = getConcreteImplementation().cast(testStep);
    }

    protected T getConcreteTestStep() {
        return this.concreteTestStep;
    }

    protected abstract Class<T> getConcreteImplementation();

    public abstract void execute() throws ValidationException;
}
