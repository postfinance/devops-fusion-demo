// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.wrapper;

import ch.postfinance.devops.fusion.demo.thirt.party.simulator.domain.wrapper.TestDataWrapper;
import ch.postfinance.devops.fusion.demo.thirt.party.simulator.issuer.foreign.domain.GetBalance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getBalances")
public class GetBalanceWrapper implements TestDataWrapper<GetBalance> {

    @XmlElement(name = "getBalance")
    private List<GetBalance> getBalances;

    @Override
    public List<GetBalance> getTestDatas() {
        return getBalances;
    }
}
