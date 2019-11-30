// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.endpoint;

import ch.postfinance.devops.fusion.demo.financial.service.api.*;
import ch.postfinance.devops.fusion.demo.financial.service.service.FinancialAuthorizationService;
import ch.postfinance.devops.fusion.demo.financial.service.service.TerminalService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Endpoint
public class FinancialAuthorizationServiceEndpoint {

    public static final String NAMESPACE_URI = "http://postfinance.ch/devops/fusion/demo/services/financial/authorization/v1";

    private final TerminalService terminalService;
    private final FinancialAuthorizationService financialAuthorizationService;

    public FinancialAuthorizationServiceEndpoint(TerminalService terminalService, FinancialAuthorizationService financialAuthorizationService) {
        this.terminalService = terminalService;
        this.financialAuthorizationService = financialAuthorizationService;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetBalance")
    public JAXBElement<GetBalanceResponse> getBalance(@RequestPayload GetBalance getBalance) {
        validateFinancialServiceMessageHeader(getBalance.getMsgHeader());

        GetBalanceResponse getBalanceResponse = new GetBalanceResponse();
        getBalanceResponse.setBalance(financialAuthorizationService.getBalance(getBalance));
        return new JAXBElement<>(new QName(NAMESPACE_URI, "GetBalanceResponse"), GetBalanceResponse.class, getBalanceResponse);
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "WithdrawCash")
    public JAXBElement<WithdrawCashResponse> withdrawCash(@RequestPayload WithdrawCash withdrawCash) {
        validateFinancialServiceMessageHeader(withdrawCash.getMsgHeader());

        WithdrawCashResponse withdrawCashResponse = new WithdrawCashResponse();
        withdrawCashResponse.setNewBalance(financialAuthorizationService.withdrawCash(withdrawCash));
        return new JAXBElement<>(new QName(NAMESPACE_URI, "WithdrawCashResponse"), WithdrawCashResponse.class, withdrawCashResponse);
    }

    private void validateFinancialServiceMessageHeader(FinancialServiceMessageHeader financialServiceMessageHeader) {
        if (!terminalService.doesTerminalExist((long) financialServiceMessageHeader.getTrmId())) {
            throw new IllegalArgumentException("This terminal is not registered! Please contact your reseller.");
        }
    }
}
