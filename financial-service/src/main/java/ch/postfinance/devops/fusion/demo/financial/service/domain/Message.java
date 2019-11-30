// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Table
@Entity
public class Message extends AbstractAuditingEntity {

    private static final String MESSAGE_ID_SEQ = "message_id_seq";

    @Id
    @GenericGenerator(name = MESSAGE_ID_SEQ, strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY, parameters = {
            @Parameter(name = "sequence_name", value = MESSAGE_ID_SEQ),
            @Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MESSAGE_ID_SEQ)
    @Column(unique = true)
    private Long id;

    private Short messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Terminal terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issuer issuer;

    public Short getMessageType() {
        return messageType;
    }

    public void setMessageType(Short messageType) {
        this.messageType = messageType;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Long getId() {
        return id;
    }
}
