package com.wexa.talentgraph.service.impl;

import com.wexa.talentgraph.dto.PersonDto;
import com.wexa.talentgraph.dto.PersonSimilarityDto;
import com.wexa.talentgraph.dto.PersonNetworkDto;
import com.wexa.talentgraph.repository.PersonRepository;
import com.wexa.talentgraph.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of PersonService.
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<PersonDto> findAll(Integer limit) {
        return personRepository.findAll(limit);
    }

    @Override
    public PersonDto findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public PersonDto save(PersonDto personDto) {
        return personRepository.save(personDto);
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonSimilarityDto> findSimilarPersons(Long personId, Integer limit) {
        return personRepository.findSimilarPersons(personId, limit);
    }

    @Override
    public PersonNetworkDto getPersonNetwork(Long personId) {
        return personRepository.getPersonNetwork(personId);
    }
}