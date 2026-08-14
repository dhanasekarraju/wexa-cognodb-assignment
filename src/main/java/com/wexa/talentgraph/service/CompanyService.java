package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.CompanyDto;
import java.util.List;

/**
 * Service for Company operations.
 */
public interface CompanyService {

    List<CompanyDto> findAll(Integer limit);

    CompanyDto findById(Long id);

    CompanyDto save(CompanyDto companyDto);
}