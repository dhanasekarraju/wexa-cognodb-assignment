package com.wexa.talentgraph.service.impl;

import com.wexa.talentgraph.dto.SkillDto;
import com.wexa.talentgraph.repository.SkillRepository;
import com.wexa.talentgraph.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of SkillService.
 */
@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<SkillDto> findAll(Integer limit) {
        return skillRepository.findAll(limit);
    }

    @Override
    public SkillDto findById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public SkillDto save(SkillDto skillDto) {
        return skillRepository.save(skillDto);
    }
}