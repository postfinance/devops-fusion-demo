// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.apptest.runner.context;

import java.util.UUID;

public class TestCaseMemento {

    private final UUID msgUuid;

    private int trmId;

    TestCaseMemento() {
        msgUuid = UUID.randomUUID();
    }

    public String getMsgUuid() {
        return msgUuid.toString();
    }

    public int getTrmId() {
        return trmId;
    }

    public void setTrmId(int trmId) {
        if (this.trmId != 0) {
            throw new IllegalArgumentException("TrmId was already assigned!");
        }

        this.trmId = trmId;
    }
}
