package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.TechnologyDto;
import java.util.List;

/**
 * Service for Technology operations.
 */
public interface TechnologyService {

    List<TechnologyDto> findAll(Integer limit);

    TechnologyDto findById(Long id);

    TechnologyDto save(TechnologyDto technologyDto);
}