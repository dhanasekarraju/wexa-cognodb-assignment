package com.wexa.talentgraph.service.impl;

import com.wexa.talentgraph.dto.CompanyDto;
import com.wexa.talentgraph.repository.CompanyRepository;
import com.wexa.talentgraph.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of CompanyService.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> findAll(Integer limit) {
        return companyRepository.findAll(limit);
    }

    @Override
    public CompanyDto findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        return companyRepository.save(companyDto);
    }
}