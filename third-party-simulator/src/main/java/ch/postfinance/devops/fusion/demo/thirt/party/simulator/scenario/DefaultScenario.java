// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.scenario;

import com.consol.citrus.simulator.scenario.AbstractSimulatorScenario;
import com.consol.citrus.simulator.scenario.Scenario;
import com.consol.citrus.simulator.scenario.ScenarioRunner;
import org.springframework.beans.factory.annotation.Value;

@Scenario("DEFAULT_SCENARIO")
public class DefaultScenario extends AbstractSimulatorScenario {

    private final String applicationName;

    public DefaultScenario(@Value("${spring.application.name}") String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void run(ScenarioRunner scenarioRunner) {
        scenarioRunner
                .soap()
                .sendFault(builder -> builder
                        .faultActor(applicationName)
                        .faultCode("SOAP-ENV:Client")
                        .faultString("No matching scenario found!"));
    }
}
