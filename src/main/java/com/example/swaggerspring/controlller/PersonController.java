package com.example.swaggerspring.controlller;

import com.example.swaggerspring.dto.PersonRequest;
import com.example.swaggerspring.entity.Person;
import com.example.swaggerspring.repository.PersonRepository;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonRepository personRepository;

    PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @ApiOperation(value = "View a list of available users", response = String.class)
    @GetMapping("/person/{id}")
    public ResponseEntity<?> getPerson(@PathVariable Long id){

        log.info("REST Request GET Person");

        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent())
            return ResponseEntity.ok(person);
        return new ResponseEntity("Person not found", HttpStatus.NOT_FOUND);
    }
    @ApiOperation(value = "Created Person", response = ResponseEntity.class)
    @PostMapping("/person")
    public ResponseEntity<?> savePerson(@RequestBody PersonRequest personRequest){
        Person person = new Person(personRequest.firstName);
        Person personSave = personRepository.save(person);
        return new ResponseEntity(person, HttpStatus.CREATED);
    }
}
