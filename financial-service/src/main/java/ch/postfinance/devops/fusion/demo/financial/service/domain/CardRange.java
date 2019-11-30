// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Table
@Entity
public class CardRange extends AbstractAuditingEntity {

    private static final String CARD_RANGE_ID_SEQ = "card_range_id_seq";

    @Id
    @GenericGenerator(name = CARD_RANGE_ID_SEQ, strategy = AbstractAuditingEntity.SEQUENCE_GENERATOR_STRATEGY, parameters = {
            @Parameter(name = "sequence_name", value = CARD_RANGE_ID_SEQ),
            @Parameter(name = "increment_size", value = "1")})
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = CARD_RANGE_ID_SEQ)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer minCardNumber;

    @Column(nullable = false)
    private Integer maxCardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issuer issuer;

    public Long getId() {
        return id;
    }

    public Integer getMinCardNumber() {
        return minCardNumber;
    }

    public void setMinCardNumber(Integer minCardNumber) {
        this.minCardNumber = minCardNumber;
    }

    public Integer getMaxCardNumber() {
        return maxCardNumber;
    }

    public void setMaxCardNumber(Integer maxCardNumber) {
        this.maxCardNumber = maxCardNumber;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }
}
