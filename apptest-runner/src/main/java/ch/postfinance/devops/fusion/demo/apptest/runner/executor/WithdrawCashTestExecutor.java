// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.executor;

import ch.postfinance.devops.fusion.demo.apptest.runner.service.FinancialAuthorizationService;
import ch.postfinance.devops.fusion.demo.apptest.runner.validation.ValidationException;
import ch.postfinance.devops.fusion.demo.financial.service.api.TestStep;
import ch.postfinance.devops.fusion.demo.financial.service.api.WithdrawCashCall;
import ch.postfinance.devops.fusion.demo.financial.service.api.WithdrawCashData;
import ch.postfinance.devops.fusion.demo.financial.service.api.WithdrawCashValidate;
import ch.postfinance.devops.fusion.demo.services.financial.authorization.v1.FinancialAuthorizationServiceException;
import ch.postfinance.devops.fusion.demo.services.financial.authorization.v1.WithdrawCashResponse;

public class WithdrawCashTestExecutor extends AbstractTestExecutor<WithdrawCashCall> {

    private final FinancialAuthorizationService financialAuthorizationService;

    public WithdrawCashTestExecutor(TestStep testStep) {
        super(testStep);

        this.financialAuthorizationService = FinancialAuthorizationService.getInstance();
    }

    @Override
    protected Class<WithdrawCashCall> getConcreteImplementation() {
        return WithdrawCashCall.class;
    }

    @Override
    public void execute() throws ValidationException {
        WithdrawCashData withdrawCashData = WithdrawCashData.class.cast(getConcreteTestStep().getData());

        try {
            WithdrawCashResponse withdrawCashResponse = financialAuthorizationService.withdrawCash(withdrawCashData);

            verifyWithdrawCashResponse(withdrawCashResponse, WithdrawCashValidate.class.cast(getConcreteTestStep().getValidate()));
        } catch (FinancialAuthorizationServiceException e) {
            // TODO: Assert on errors
            throw new IllegalArgumentException("Cannot assert on " + FinancialAuthorizationServiceException.class, e);
        }
    }

    private void verifyWithdrawCashResponse(WithdrawCashResponse withdrawCashResponse, WithdrawCashValidate withdrawCashValidate) throws ValidationException {
        Long expected = withdrawCashValidate.getNewBalance();
        Long actual = withdrawCashResponse.getNewBalance();

        if (expected != null && !expected.equals(actual)) {
            throw new ValidationException("newBalance", expected, actual);
        }
    }
}
