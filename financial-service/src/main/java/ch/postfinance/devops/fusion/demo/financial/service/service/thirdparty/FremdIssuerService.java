// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.service.thirdparty;

import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.fremd.v1.FremdIssuerSimServicePortType;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.fremd.v1.GeldAbheben;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.fremd.v1.KartenDaten;
import ch.postfinance.devops.fusion.demo.services.sim.foreignissuer.fremd.v1.KontostandAbfragen;
import org.springframework.stereotype.Service;

@Service
public class FremdIssuerService implements ThirdPartyIssuerService {

    private final FremdIssuerSimServicePortType fremdIssuerSimServicePortType;

    public FremdIssuerService(FremdIssuerSimServicePortType fremdIssuerSimServicePortType) {
        this.fremdIssuerSimServicePortType = fremdIssuerSimServicePortType;
    }

    @Override
    public long getBalance(String kartenNummer) {
        KartenDaten kartenDaten = new KartenDaten();
        kartenDaten.setKartenNummer(kartenNummer);

        KontostandAbfragen kontostandAbfragen = new KontostandAbfragen();
        kontostandAbfragen.setKartenDaten(kartenDaten);

        return fremdIssuerSimServicePortType.kontostandAbfragen(kontostandAbfragen)
                .getBetrag();
    }

    @Override
    public long withdrawCash(String kartenNummer, long betrag) {
        KartenDaten kartenDaten = new KartenDaten();
        kartenDaten.setKartenNummer(kartenNummer);

        GeldAbheben geldAbheben = new GeldAbheben();
        geldAbheben.setBetrag(betrag);
        geldAbheben.setKartenDaten(kartenDaten);

        return fremdIssuerSimServicePortType.geldAbheben(geldAbheben)
                .getNeuerKontostand();
    }
}
