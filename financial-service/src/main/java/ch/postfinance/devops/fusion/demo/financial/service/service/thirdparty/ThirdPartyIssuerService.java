// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.service.thirdparty;

public interface ThirdPartyIssuerService {

    long getBalance(String cardNumber);

    long withdrawCash(String cardNumber, long amount);
}
