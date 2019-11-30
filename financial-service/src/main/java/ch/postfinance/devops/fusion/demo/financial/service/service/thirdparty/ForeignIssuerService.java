// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.service.thirdparty;

import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.foreign.v1.CardData;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.foreign.v1.ForeignIssuerSimServicePortType;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.foreign.v1.GetBalance;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.foreign.v1.WithdrawCash;
import org.springframework.stereotype.Service;

@Service
public class ForeignIssuerService implements ThirdPartyIssuerService {

    private final ForeignIssuerSimServicePortType foreignIssuerSimServicePortType;

    public ForeignIssuerService(ForeignIssuerSimServicePortType foreignIssuerSimServicePortType) {
        this.foreignIssuerSimServicePortType = foreignIssuerSimServicePortType;
    }

    @Override
    public long getBalance(String cardNumber) {
        CardData cardData = new CardData();
        cardData.setCardNumber(cardNumber);

        GetBalance getBalance = new GetBalance();
        getBalance.setCardData(cardData);

        return foreignIssuerSimServicePortType.getBalance(getBalance)
                .getBalance();
    }

    @Override
    public long withdrawCash(String cardNumber, long amount) {
        CardData cardData = new CardData();
        cardData.setCardNumber(cardNumber);

        WithdrawCash withdrawCash = new WithdrawCash();
        withdrawCash.setAmount(amount);
        withdrawCash.setCardData(cardData);

        return foreignIssuerSimServicePortType.withdrawCash(withdrawCash)
                .getNewBalance();
    }
}
