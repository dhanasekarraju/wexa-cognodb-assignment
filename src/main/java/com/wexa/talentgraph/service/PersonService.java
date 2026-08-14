package com.wexa.talentgraph.service;

import com.wexa.talentgraph.dto.PersonDto;
import com.wexa.talentgraph.dto.PersonSimilarityDto;
import com.wexa.talentgraph.dto.PersonNetworkDto;
import java.util.List;

/**
 * Service for Person operations.
 */
public interface PersonService {

    List<PersonDto> findAll(Integer limit);

    PersonDto findById(Long id);

    PersonDto save(PersonDto personDto);

    void deleteById(Long id);

    List<PersonSimilarityDto> findSimilarPersons(Long personId, Integer limit);

    PersonNetworkDto getPersonNetwork(Long personId);
}