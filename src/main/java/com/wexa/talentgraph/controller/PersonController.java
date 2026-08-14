package com.wexa.talentgraph.controller;

import com.wexa.talentgraph.dto.PersonDto;
import com.wexa.talentgraph.dto.PersonSimilarityDto;
import com.wexa.talentgraph.dto.PersonNetworkDto;
import com.wexa.talentgraph.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Person entities.
 */
@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Get all persons with optional limit.
     */
    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPeople(
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<PersonDto> people = personService.findAll(limit);
        return ResponseEntity.ok(people);
    }

    /**
     * Get person by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable("id") Long id) {
        PersonDto person = personService.findById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create or update a person.
     */
    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) {
        PersonDto savedPerson = personService.save(personDto);
        return ResponseEntity.ok(savedPerson);
    }

    /**
     * Delete a person by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get similar persons based on skills, technologies, domains, and project experience.
     * This demonstrates graph traversal for finding similar talent.
     */
    @GetMapping("/{id}/similar")
    public ResponseEntity<List<PersonSimilarityDto>> getSimilarPersons(
            @PathVariable("id") Long id,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<PersonSimilarityDto> similarPersons = personService.findSimilarPersons(id, limit);
        return ResponseEntity.ok(similarPersons);
    }

    /**
     * Get the network neighborhood for a person.
     * Returns nodes and edges suitable for graph visualization.
     */
    @GetMapping("/{id}/network")
    public ResponseEntity<PersonNetworkDto> getPersonNetwork(@PathVariable("id") Long id) {
        PersonNetworkDto network = personService.getPersonNetwork(id);
        return ResponseEntity.ok(network);
    }
}