package com.wexa.talentgraph.service.impl;

import com.wexa.talentgraph.dto.DomainDto;
import com.wexa.talentgraph.repository.DomainRepository;
import com.wexa.talentgraph.service.DomainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of DomainService.
 */
@Service
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;

    public DomainServiceImpl(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public List<DomainDto> findAll(Integer limit) {
        return domainRepository.findAll(limit);
    }

    @Override
    public DomainDto findById(Long id) {
        return domainRepository.findById(id);
    }

    @Override
    public DomainDto save(DomainDto domainDto) {
        return domainRepository.save(domainDto);
    }
}