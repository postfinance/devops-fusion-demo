// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.financial.service.service;

import ch.postfinance.devops.fusion.demo.financial.service.domain.repository.TerminalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TerminalService {

    private final TerminalRepository terminalRepository;

    public TerminalService(TerminalRepository terminalRepository) {
        this.terminalRepository = terminalRepository;
    }

    @Transactional(readOnly = true)
    public boolean doesTerminalExist(Long terminalId) {
        return terminalRepository.findById(terminalId).isPresent();
    }
}
