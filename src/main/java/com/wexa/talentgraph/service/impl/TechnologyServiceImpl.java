package com.wexa.talentgraph.service.impl;

import com.wexa.talentgraph.dto.TechnologyDto;
import com.wexa.talentgraph.repository.TechnologyRepository;
import com.wexa.talentgraph.service.TechnologyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of TechnologyService.
 */
@Service
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;

    public TechnologyServiceImpl(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public List<TechnologyDto> findAll(Integer limit) {
        return technologyRepository.findAll(limit);
    }

    @Override
    public TechnologyDto findById(Long id) {
        return technologyRepository.findById(id);
    }

    @Override
    public TechnologyDto save(TechnologyDto technologyDto) {
        return technologyRepository.save(technologyDto);
    }
}