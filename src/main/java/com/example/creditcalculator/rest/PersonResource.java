package com.example.creditcalculator.rest;

import com.example.creditcalculator.model.PersonDTO;
import com.example.creditcalculator.service.PersonService;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {

    private final PersonService personService;

    public PersonResource(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:3454")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3454")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable final UUID id) {
        return ResponseEntity.ok(personService.get(id));
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3454")
    public ResponseEntity<UUID> createPerson(@RequestBody @Valid final PersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    @CrossOrigin(origins = "http://localhost:3454")
    public ResponseEntity<Void> updatePerson(@RequestBody @Valid final PersonDTO personDTO) {
        personService.update(personDTO.getId(), personDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    @CrossOrigin(origins = "http://localhost:3454")
    public ResponseEntity<Void> deletePerson(@RequestBody PersonDTO personDTO) {
        personService.delete(personDTO.getId());
        return ResponseEntity.noContent().build();
    }

}
