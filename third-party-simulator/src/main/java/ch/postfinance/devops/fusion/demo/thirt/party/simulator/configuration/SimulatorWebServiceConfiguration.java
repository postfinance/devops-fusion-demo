// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.configuration;

import com.consol.citrus.endpoint.EndpointAdapter;
import com.consol.citrus.endpoint.adapter.StaticEndpointAdapter;
import com.consol.citrus.http.message.HttpMessage;
import com.consol.citrus.message.Message;
import com.consol.citrus.simulator.config.SimulatorConfigurationProperties;
import com.consol.citrus.simulator.scenario.mapper.ContentBasedXPathScenarioMapper;
import com.consol.citrus.simulator.scenario.mapper.ScenarioMapper;
import com.consol.citrus.simulator.ws.SimulatorWebServiceConfigurationProperties;
import com.consol.citrus.simulator.ws.SimulatorWebServiceConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class SimulatorWebServiceConfiguration implements SimulatorWebServiceConfigurer {

    @Override
    public ScenarioMapper scenarioMapper() {
        return new ContentBasedXPathScenarioMapper().addXPathExpression("local-name(/*)");
    }

    @Override
    public EndpointAdapter fallbackEndpointAdapter() {
        return new StaticEndpointAdapter() {

            @Override
            protected Message handleMessageInternal(Message message) {
                return new HttpMessage().status(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @Override
    public Long exceptionDelay(SimulatorConfigurationProperties simulatorConfiguration) {
        return simulatorConfiguration.getExceptionDelay();
    }

    @Override
    public String servletMapping(SimulatorWebServiceConfigurationProperties simulatorWebServiceConfiguration) {
        return simulatorWebServiceConfiguration.getServletMapping();
    }
}
