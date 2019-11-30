// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.service;

import ch.postfinance.devops.fusion.demo.financial.service.api.GetBalance;
import ch.postfinance.devops.fusion.demo.financial.service.api.WithdrawCash;
import ch.postfinance.devops.fusion.demo.financial.service.domain.Issuer;
import ch.postfinance.devops.fusion.demo.financial.service.domain.repository.CardRangeCRUDRepository;
import ch.postfinance.devops.fusion.demo.financial.service.service.thirdparty.ForeignIssuerService;
import ch.postfinance.devops.fusion.demo.financial.service.service.thirdparty.FremdIssuerService;
import ch.postfinance.devops.fusion.demo.financial.service.service.thirdparty.ThirdPartyIssuerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinancialAuthorizationService {

    private final CardRangeCRUDRepository cardRangeRepository;

    private final ForeignIssuerService foreignIssuerService;
    private final FremdIssuerService fremdIssuerService;

    public FinancialAuthorizationService(CardRangeCRUDRepository cardRangeRepository, ForeignIssuerService foreignIssuerService, FremdIssuerService fremdIssuerService) {
        this.cardRangeRepository = cardRangeRepository;

        this.foreignIssuerService = foreignIssuerService;
        this.fremdIssuerService = fremdIssuerService;
    }

    @Transactional(readOnly = true)
    public long getBalance(GetBalance getBalance) {
        String cardNumber = getBalance.getCardData().getCardNumber();
        Issuer issuer = findIssuerByCardNumber(cardNumber);

        return getServiceByIssuer(issuer)
                .getBalance(cardNumber);
    }

    @Transactional(readOnly = true)
    public long withdrawCash(WithdrawCash withdrawCash) {
        String cardNumber = withdrawCash.getCardData().getCardNumber();
        Issuer issuer = findIssuerByCardNumber(cardNumber);

        return getServiceByIssuer(issuer)
                .withdrawCash(cardNumber, withdrawCash.getAmount());
    }

    private Issuer findIssuerByCardNumber(String cardNumber) {
        Integer numericCardNumber = Integer.valueOf(cardNumber);

        return cardRangeRepository.findByMinCardNumberLessThanAndMaxCardNumberGreaterThan(numericCardNumber, numericCardNumber)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find issuer within card range!"))
                .getIssuer();
    }

    private ThirdPartyIssuerService getServiceByIssuer(Issuer issuer) {
        switch (issuer.getName()) {
            case "FremdIssuer":
                return fremdIssuerService;
            case "ForeignIssuer":
                return foreignIssuerService;
            default:
                throw new IllegalArgumentException("This issuer does not exist!");
        }
    }
}
