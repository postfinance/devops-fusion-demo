// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.service;

import ch.postfinance.devops.fusion.demo.apptest.runner.context.TestCaseMemento;
import ch.postfinance.devops.fusion.demo.apptest.runner.context.ThreadLocalApplicationTestContext;
import ch.postfinance.devops.fusion.demo.financial.service.api.GetBalanceData;
import ch.postfinance.devops.fusion.demo.financial.service.api.WithdrawCashData;
import ch.postfinance.devops.fusion.demo.services.financial.authorization.v1.*;
import ch.postfinance.devops.fusion.demo.services.financial.types.v1.CardData;
import ch.postfinance.devops.fusion.demo.services.financial.types.v1.FinancialServiceMessageHeader;

import javax.xml.ws.BindingProvider;

public class FinancialAuthorizationService {

    private final ThreadLocalApplicationTestContext threadLocalApplicationTestContext = new ThreadLocalApplicationTestContext();

    private final FinancialAuthorizationServicePortType financialAuthorizationServicePortType;

    private FinancialAuthorizationService() {
        this.financialAuthorizationServicePortType = constructFinancialAuthorizationServicePortType();
    }

    public static FinancialAuthorizationService getInstance() {
        return FinancialAuthorizationServiceHolder.INSTANCE;
    }

    private FinancialAuthorizationServicePortType constructFinancialAuthorizationServicePortType() {
        FinancialAuthorizationServicePortType financialAuthorizationServicePortType = new ch.postfinance.devops.fusion.demo.services.financial.authorization.v1.FinancialAuthorizationService()
                .getFinancialAuthorizationServicePortType();

        ((BindingProvider) financialAuthorizationServicePortType).getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, threadLocalApplicationTestContext.get().getFinancialAuthorizationServiceEndpoint());

        return financialAuthorizationServicePortType;
    }

    public GetBalanceResponse getBalance(GetBalanceData getBalanceData) throws FinancialAuthorizationServiceException {
        CardData cardData = new CardData();
        cardData.setCardNumber(getBalanceData.getCardData().getCardNumber());

        GetBalance getBalance = new GetBalance();
        getBalance.setCardData(cardData);
        getBalance.setMsgHeader(constructFinancialServiceMessageHeader());

        return financialAuthorizationServicePortType.getBalance(getBalance);
    }

    public WithdrawCashResponse withdrawCash(WithdrawCashData withdrawCashData) throws FinancialAuthorizationServiceException {
        CardData cardData = new CardData();
        cardData.setCardNumber(withdrawCashData.getCardData().getCardNumber());

        WithdrawCash withdrawCash = new WithdrawCash();
        withdrawCash.setAmount(withdrawCashData.getAmount());
        withdrawCash.setCardData(cardData);
        withdrawCash.setMsgHeader(constructFinancialServiceMessageHeader());

        return financialAuthorizationServicePortType.withdrawCash(withdrawCash);
    }

    private FinancialServiceMessageHeader constructFinancialServiceMessageHeader() {
        TestCaseMemento testCaseMemento = threadLocalApplicationTestContext.get().getTestCaseMemento();

        FinancialServiceMessageHeader financialServiceMessageHeader = new FinancialServiceMessageHeader();
        financialServiceMessageHeader.setMsgUuid(testCaseMemento.getMsgUuid());
        financialServiceMessageHeader.setTrmId(testCaseMemento.getTrmId());

        return financialServiceMessageHeader;
    }

    private static class FinancialAuthorizationServiceHolder {

        static final FinancialAuthorizationService INSTANCE = getInstance();

        static FinancialAuthorizationService getInstance() {
            return new FinancialAuthorizationService();
        }
    }
}
