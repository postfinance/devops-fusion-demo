// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.repository;

import ch.postfinance.devops.fusion.demo.thirt.party.simulator.annotation.TestDataRepository;
import ch.postfinance.devops.fusion.demo.thirt.party.simulator.domain.repository.AbstractTestDataRepository;
import ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.GetBalance;
import ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.wrapper.GetBalanceWrapper;

import javax.xml.bind.JAXBException;
import java.util.stream.IntStream;

@TestDataRepository
public class GetBalanceRepository extends AbstractTestDataRepository<GetBalance, GetBalanceWrapper> {

    private static final String RESOURCE_LOCATION = "simulator/foreignissuer/data/GetBalance.xml";
    private static final String DEFAULT_CARD_NUMBER = "DEFAULT";

    public GetBalanceRepository() throws JAXBException {
        super(RESOURCE_LOCATION, GetBalanceWrapper.class);
    }

    @Override
    protected int getIndexOfDefaultEntity() {
        return IntStream.range(0, getTestData().size())
                .filter(i -> getTestData().get(i).getGetBalanceRequest().getCardNumber().equals(DEFAULT_CARD_NUMBER))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Test data does not contain a default value!"));
    }

    public GetBalance findOneByCardNumber(String cardNumber) {
        log.info("Querying for " + GetBalance.class + " { cardNumber: '" + cardNumber + "' }");

        return getTestData().stream()
                .filter(getBalance -> getBalance.getGetBalanceRequest().getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(getDefaultResponse());
    }
}
