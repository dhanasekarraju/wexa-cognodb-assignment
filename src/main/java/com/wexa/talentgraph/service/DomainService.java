package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.DomainDto;
import java.util.List;

/**
 * Service for Domain operations.
 */
public interface DomainService {

    List<DomainDto> findAll(Integer limit);

    DomainDto findById(Long id);

    DomainDto save(DomainDto domainDto);
}