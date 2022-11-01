package com.example.creditcalculator.rest;

import com.example.creditcalculator.domain.User;
import com.example.creditcalculator.model.PersonDTO;
import com.example.creditcalculator.model.UserDTO;
import com.example.creditcalculator.repos.UserRepository;
import com.example.creditcalculator.service.PersonService;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import com.example.creditcalculator.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {

    private final PersonService personService;
    private final UserService userService;
    private final UserRepository userRepository;

    public PersonResource(final PersonService personService, UserService userService, UserRepository userRepository) {
        this.personService = personService;
        this.userService = userService;
        this.userRepository = userRepository;
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


    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3454")
    public ResponseEntity<Long> registerPerson(@RequestBody final UserDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }


    //get user role
    @PostMapping("/role")
    @CrossOrigin(origins = "http://localhost:3454")
    public UserDTO getUserRole(@RequestBody final UserDTO userDTO) {
     Long id =  userRepository.findByName(userDTO.getName()).get(0).getId();
        userService.get(id);
        return   userService.get(id);
    }

}
