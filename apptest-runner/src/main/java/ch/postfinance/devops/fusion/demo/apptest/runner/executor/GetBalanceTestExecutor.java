// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.executor;

import ch.postfinance.devops.fusion.demo.apptest.runner.service.FinancialAuthorizationService;
import ch.postfinance.devops.fusion.demo.apptest.runner.validation.ValidationException;
import ch.postfinance.devops.fusion.demo.financial.service.api.GetBalanceCall;
import ch.postfinance.devops.fusion.demo.financial.service.api.GetBalanceData;
import ch.postfinance.devops.fusion.demo.financial.service.api.GetBalanceValidate;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestStep;
import ch.postfinance.devops.fusion.demo.services.financial.authorization.v1.FinancialAuthorizationServiceException;
import ch.postfinance.devops.fusion.demo.services.financial.authorization.v1.GetBalanceResponse;

public class GetBalanceTestExecutor extends AbstractTestExecutor<GetBalanceCall> {

    private final FinancialAuthorizationService financialAuthorizationService;

    public GetBalanceTestExecutor(TestStep testStep) {
        super(testStep);

        this.financialAuthorizationService = FinancialAuthorizationService.getInstance();
    }

    @Override
    protected Class<GetBalanceCall> getConcreteImplementation() {
        return GetBalanceCall.class;
    }

    @Override
    public void execute() throws ValidationException {
        GetBalanceData getBalanceData = GetBalanceData.class.cast(getConcreteTestStep().getData());

        try {
            GetBalanceResponse getBalanceResponse = financialAuthorizationService.getBalance(getBalanceData);

            verifyGetBalanceResponse(getBalanceResponse, GetBalanceValidate.class.cast(getConcreteTestStep().getValidate()));
        } catch (FinancialAuthorizationServiceException e) {
            // TODO: Assert on errors
            throw new IllegalArgumentException("Cannot assert on " + FinancialAuthorizationServiceException.class, e);
        }
    }

    private void verifyGetBalanceResponse(GetBalanceResponse getBalanceResponse, GetBalanceValidate getBalanceValidate) throws ValidationException {
        Long expected = getBalanceValidate.getBalance();
        Long actual = getBalanceResponse.getBalance();

        if (expected != null && !expected.equals(actual)) {
            throw new ValidationException("balance", expected, actual);
        }
    }
}
