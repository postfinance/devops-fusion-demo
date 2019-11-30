// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.domain.repository;

import ch.postfinance.devops.fusion.demo.financial.service.domain.CardRange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRangeCRUDRepository extends CrudRepository<CardRange, Long> {

    Optional<CardRange> findByMinCardNumberLessThanAndMaxCardNumberGreaterThan(Integer minCardNumber, Integer maxCardNumber);
}
