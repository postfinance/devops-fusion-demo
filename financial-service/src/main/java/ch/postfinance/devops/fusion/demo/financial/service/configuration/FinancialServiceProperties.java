package ch.postfinance.devops.fusion.demo.financial.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("financial-service")
public class FinancialServiceProperties {

    private Issuer issuer;

    public Issuer getIssuer() {
        return this.issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public static class Issuer {

        private ForeignIssuer foreignIssuer;
        private FremdIssuer fremdIssuer;

        public ForeignIssuer getForeignIssuer() {
            return this.foreignIssuer;
        }

        public void setForeignIssuer(ForeignIssuer foreignIssuer) {
            this.foreignIssuer = foreignIssuer;
        }

        public FremdIssuer getFremdIssuer() {
            return fremdIssuer;
        }

        public void setFremdIssuer(FremdIssuer fremdIssuer) {
            this.fremdIssuer = fremdIssuer;
        }

        public static class ForeignIssuer {

            private String url;

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class FremdIssuer {

            private String url;

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
