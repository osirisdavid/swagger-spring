package com.example.swaggerspring.controlller;

import com.example.swaggerspring.dto.PersonRequest;
import com.example.swaggerspring.dto.PersonResponse;
import com.example.swaggerspring.dto.Person;
import com.example.swaggerspring.repository.PersonRepository;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    //@ApiOperation(value = "View a list of available users", response = String.class)
    @Operation(summary = "Get a person by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Person.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content) })
    @GetMapping("/person/{id}")
    public ResponseEntity<?> getPerson(@PathVariable Long id){

        log.info("REST Request GET Person");

        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent())
            return ResponseEntity.ok(person);
        return new ResponseEntity("Person not found", HttpStatus.NOT_FOUND);
    }
    //@ApiOperation(value = "Created Person", response = ResponseEntity.class)
    @PostMapping("/person")
    public ResponseEntity<PersonResponse> savePerson(@RequestBody PersonRequest personRequest){
        Person person = new Person(personRequest.firstName);
        Person personSave = personRepository.save(person);
        return new ResponseEntity(new PersonResponse(personSave.getIdPerson(), personSave.getFirstName()), HttpStatus.CREATED);
    }

}
