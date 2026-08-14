package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.SkillDto;
import java.util.List;

/**
 * Service for Skill operations.
 */
public interface SkillService {

    List<SkillDto> findAll(Integer limit);

    SkillDto findById(Long id);

    SkillDto save(SkillDto skillDto);
}