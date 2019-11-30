// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.configuration;

import ch.postfinance.devops.fusion.demo.financial.service.endpoint.FinancialAuthorizationServiceEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

    private final ApplicationContext applicationContext;

    public WebServiceConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet() {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/services/ws/*");
    }

    @Bean
    public DefaultWsdl11Definition defaultWsdl11Definition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("FinancialAuthorizationServicePortType");
        wsdl11Definition.setLocationUri("/services/ws");
        wsdl11Definition.setTargetNamespace(FinancialAuthorizationServiceEndpoint.NAMESPACE_URI);
        wsdl11Definition.setSchema(financialAuthorizationServiceSchema());
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema financialAuthorizationServiceSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/financial/authorization/FinancialAuthorizationServiceTypes_v1.xsd"));
    }
}
