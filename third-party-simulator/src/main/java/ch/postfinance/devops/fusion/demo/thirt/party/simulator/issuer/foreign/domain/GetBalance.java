// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Copyright 2020 by PostFinance Ltd - all rights reserved
 */
public class GetBalance {

    @XmlElement
    private GetBalanceRequest getBalanceRequest;

    @XmlElement
    private GetBalanceResponse getBalanceResponse;

    public GetBalanceRequest getGetBalanceRequest() {
        return getBalanceRequest;
    }

    public void setGetBalanceRequest(GetBalanceRequest getBalanceRequest) {
        this.getBalanceRequest = getBalanceRequest;
    }

    public GetBalanceResponse getGetBalanceResponse() {
        return getBalanceResponse;
    }

    public void setGetBalanceResponse(GetBalanceResponse getBalanceResponse) {
        this.getBalanceResponse = getBalanceResponse;
    }

    public static class GetBalanceRequest {

        @XmlElement
        private String cardNumber;

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

    public static class GetBalanceResponse {

        @XmlElement
        public Long balance;

        public Long getBalance() {
            return balance;
        }

        public void setBalance(Long balance) {
            this.balance = balance;
        }
    }
}
