package ch.postfinance.devops.fusion.demo.financial.service.configuration;

import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.foreign.v1.ForeignIssuerSimService;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.foreign.v1.ForeignIssuerSimServicePortType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.xml.ws.BindingProvider;
import java.io.IOException;

@Configuration
public class ForeignIssuerPortTypeConfiguration {

    private final FinancialServiceProperties financialServiceProperties;

    public ForeignIssuerPortTypeConfiguration(FinancialServiceProperties financialServiceProperties) {
        this.financialServiceProperties = financialServiceProperties;
    }

    @Bean
    public ForeignIssuerSimServicePortType foreignIssuerServicePortType() throws IOException {
        ForeignIssuerSimServicePortType foreignIssuerSimServicePortType = new ForeignIssuerSimService(
                new ClassPathResource("wsdl/sim/foreignissuer/foreign/ForeignIssuerSimService_v1.wsdl").getURL())
                .getForeignIssuerSimServicePortType();

        ((BindingProvider) foreignIssuerSimServicePortType).getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, financialServiceProperties.getIssuer().getForeignIssuer().getUrl());

        return foreignIssuerSimServicePortType;
    }
}
