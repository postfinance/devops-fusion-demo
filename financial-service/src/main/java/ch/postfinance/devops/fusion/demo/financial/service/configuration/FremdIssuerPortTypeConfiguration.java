package ch.postfinance.devops.fusion.demo.financial.service.configuration;

import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.fremd.v1.FremdIssuerSimService;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.fremd.v1.FremdIssuerSimServicePortType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.xml.ws.BindingProvider;
import java.io.IOException;

@Configuration
public class FremdIssuerPortTypeConfiguration {

    private final FinancialServiceProperties financialServiceProperties;

    public FremdIssuerPortTypeConfiguration(FinancialServiceProperties financialServiceProperties) {
        this.financialServiceProperties = financialServiceProperties;
    }

    @Bean
    public FremdIssuerSimServicePortType fremdIssuerServicePortType() throws IOException {
        FremdIssuerSimServicePortType fremdIssuerSimServicePortType = new FremdIssuerSimService(
                new ClassPathResource("wsdl/sim/foreignissuer/fremd/FremdIssuerSimService_v1.wsdl").getURL())
                .getFremdIssuerSimServicePortType();

        ((BindingProvider) fremdIssuerSimServicePortType).getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, financialServiceProperties.getIssuer().getFremdIssuer().getUrl());

        return fremdIssuerSimServicePortType;
    }
}
