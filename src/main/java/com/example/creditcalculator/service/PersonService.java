package com.example.creditcalculator.service;

import com.example.creditcalculator.domain.Person;
import com.example.creditcalculator.model.PersonDTO;
import com.example.creditcalculator.repos.PersonRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll() {
        return personRepository.findAll(Sort.by("id"))
                .stream()
                .map(person -> mapToDTO(person, new PersonDTO()))
                .collect(Collectors.toList());
    }

    public PersonDTO get(final UUID id) {
        return personRepository.findById(id)
                .map(person -> mapToDTO(person, new PersonDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final PersonDTO personDTO) {
        final Person person = new Person();
        mapToEntity(personDTO, person);
        return personRepository.save(person).getId();
    }

    public void update(final UUID id, final PersonDTO personDTO) {
        final Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(personDTO, person);
        personRepository.save(person);
    }

    public void delete(final UUID id) {
        personRepository.deleteById(id);
    }

    private PersonDTO mapToDTO(final Person person, final PersonDTO personDTO) {
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setSurname(person.getSurname());
        personDTO.setDateOfBirth(person.getDateOfBirth());
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setDateOfBirth(personDTO.getDateOfBirth());
        return person;
    }

}
