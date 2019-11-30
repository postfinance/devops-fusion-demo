// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Table
@Entity
public class Issuer extends AbstractAuditingEntity {

    private static final String ISSUER_ID_SEQ = "issuer_id_seq";

    @Id
    @GenericGenerator(name = ISSUER_ID_SEQ, strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY, parameters = {
            @Parameter(name = "sequence_name", value = ISSUER_ID_SEQ),
            @Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ISSUER_ID_SEQ)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "issuer", fetch = FetchType.LAZY)
    private Set<Message> messages = Collections.emptySet();

    @OneToMany(mappedBy = "issuer", fetch = FetchType.LAZY)
    private Set<CardRange> cardRanges = Collections.emptySet();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<CardRange> getCardRanges() {
        return cardRanges;
    }

    public void setCardRanges(Set<CardRange> cardRanges) {
        this.cardRanges = cardRanges;
    }
}
