// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.scenario;

import ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.repository.WithdrawCashRepository;
import ch.postfinance.devops.fusion.demo.thirt.party.simulator.xpath.XPathMatcher;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.simulator.scenario.AbstractSimulatorScenario;
import com.consol.citrus.simulator.scenario.Scenario;
import com.consol.citrus.simulator.scenario.ScenarioRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

@Scenario("WithdrawCash")
public class WithdrawCashScenario extends AbstractSimulatorScenario {

    private static final String QNAME = "http://postfinance.ch/devops/fusion/demo/services/sim/foreignissuer/foreign/v1";

    private static final String CARD_NUMBER_VARIABLE_NAME = "WithdrawCashScenario:CardNumber";
    private static final String NEW_BALANCE_VARIABLE_NAME = "WithdrawCashScenario:NewBalance";

    private final WithdrawCashRepository withdrawCashRepository;

    public WithdrawCashScenario(WithdrawCashRepository withdrawCashRepository) {
        this.withdrawCashRepository = withdrawCashRepository;
    }

    @Override
    public void run(ScenarioRunner scenarioRunner) {
        scenarioRunner
                .soap()
                .receive(configurer -> configurer
                        .xpath(XPathMatcher.rootNodeMatchingQName("WithdrawCash", QNAME), Boolean.TRUE)
                        .extractFromPayload(XPathMatcher.nodeMatchingLocalNameAndQName("CardNumber", QNAME), CARD_NUMBER_VARIABLE_NAME));

        TestContext testContext = scenarioRunner.getTestContext();

        Long newBalance = withdrawCashRepository.findOneByCardNumber(CARD_NUMBER_VARIABLE_NAME)
                .getWithdrawCashResponse()
                .getNewBalance();

        testContext.setVariable(NEW_BALANCE_VARIABLE_NAME, newBalance);

        try (InputStream templateInputStream = new ClassPathResource("simulator/foreignissuer/template/WithdrawCashResponse.xml").getInputStream()) {
            scenarioRunner
                    .soap()
                    .send(configurer -> configurer
                            .payload(new InputStreamResource(templateInputStream)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
