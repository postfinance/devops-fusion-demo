// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Table
@Entity
public class Terminal extends AbstractAuditingEntity {

    @Id
    @Column(unique = true, nullable = false)
    private Long terminalId;

    @OneToMany(mappedBy = "terminal", fetch = FetchType.LAZY)
    private Set<Message> messages = Collections.emptySet();

    public Long getTerminalId() {
        return terminalId;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
