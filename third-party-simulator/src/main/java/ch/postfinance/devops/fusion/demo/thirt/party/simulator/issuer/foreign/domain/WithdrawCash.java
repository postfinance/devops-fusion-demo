// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Copyright 2020 by PostFinance Ltd - all rights reserved
 */
public class WithdrawCash {

    @XmlElement
    private WithdrawCashRequest withdrawCashRequest;

    @XmlElement
    private WithdrawCashResponse withdrawCashResponse;

    public WithdrawCashRequest getWithdrawCashRequest() {
        return withdrawCashRequest;
    }

    public void setWithdrawCashRequest(WithdrawCashRequest withdrawCashRequest) {
        this.withdrawCashRequest = withdrawCashRequest;
    }

    public WithdrawCashResponse getWithdrawCashResponse() {
        return withdrawCashResponse;
    }

    public void setWithdrawCashResponse(WithdrawCashResponse withdrawCashResponse) {
        this.withdrawCashResponse = withdrawCashResponse;
    }

    public static class WithdrawCashRequest {

        @XmlElement
        private String cardNumber;

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }
    }

    public static class WithdrawCashResponse {

        @XmlElement
        public Long newBalance;

        public Long getNewBalance() {
            return newBalance;
        }

        public void setNewBalance(Long newBalance) {
            this.newBalance = newBalance;
        }
    }
}
