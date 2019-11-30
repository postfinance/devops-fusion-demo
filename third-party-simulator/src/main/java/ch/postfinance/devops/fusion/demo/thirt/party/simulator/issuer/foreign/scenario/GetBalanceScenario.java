// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.scenario;

import ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.repository.GetBalanceRepository;
import ch.postfinance.devops.fusion.demo.thirt.party.simulator.xpath.XPathMatcher;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.simulator.scenario.AbstractSimulatorScenario;
import com.consol.citrus.simulator.scenario.Scenario;
import com.consol.citrus.simulator.scenario.ScenarioRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

@Scenario("GetBalance")
public class GetBalanceScenario extends AbstractSimulatorScenario {

    private static final String QNAME = "http://postfinance.ch/devops/fusion/demo/services/sim/foreignissuer/foreign/v1";

    private static final String CARD_NUMBER_VARIABLE_NAME = "GetBalanceScenario:CardNumber";
    private static final String BALANCE_VARIABLE_NAME = "GetBalanceScenario:Balance";

    private final GetBalanceRepository getBalanceRepository;

    public GetBalanceScenario(GetBalanceRepository getBalanceRepository) {
        this.getBalanceRepository = getBalanceRepository;
    }

    @Override
    public void run(ScenarioRunner scenarioRunner) {
        scenarioRunner
                .soap()
                .receive(configurer -> configurer
                        .xpath(XPathMatcher.rootNodeMatchingQName("GetBalance", QNAME), Boolean.TRUE)
                        .extractFromPayload(XPathMatcher.nodeMatchingLocalNameAndQName("CardNumber", QNAME), CARD_NUMBER_VARIABLE_NAME));

        TestContext testContext = scenarioRunner.getTestContext();

        Long balance = getBalanceRepository.findOneByCardNumber(testContext.getVariable(CARD_NUMBER_VARIABLE_NAME))
                .getGetBalanceResponse()
                .getBalance();

        testContext.setVariable(BALANCE_VARIABLE_NAME, balance);

        try (InputStream templateInputStream = new ClassPathResource("simulator/foreignissuer/template/GetBalanceResponse.xml").getInputStream()) {
            scenarioRunner
                    .soap()
                    .send(configurer -> configurer
                            .payload(new InputStreamResource(templateInputStream)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
