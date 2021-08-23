package com.answerdigital.colourstest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import springfox.documentation.swagger2.mappers.ModelMapper;

@RestController
@RequestMapping("/People")
public class PeopleController {
    

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        // TODO STEP 1
        //
        // Implement a JSON endpoint that returns the full list
        // of people from the PeopleRepository. If there are zero
        // people returned from PeopleRepository then an empty
        // JSON array should be returned.

        return new ResponseEntity(peopleRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        // TODO: Step 2
        //
        // Implement a JSON endpoint that returns a single person
        // from the PeopleRepository based on the id parameter.
        // If null is returned from the PeopleRepository with
        // the supplied id then a NotFound should be returned.
        Optional<Person> p = peopleRepository.findById(id);
        if(!p.isEmpty()){
          return new ResponseEntity(p, HttpStatus.OK);  
        }
        else{
          return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        // TODO STEP 3
        //
        // Implement an endpoint that recieves a JSON object to
        // update a person using the PeopleRepository based on
        // the id parameter. Once the person has been sucessfullly
        // updated, the person should be returned from the endpoint.
        // If null is returned from the PeopleRepository then a
        // NotFound should be returned.
        
        if(!peopleRepository.findById(id).isEmpty()){
            Person p = peopleRepository.getOne(id);
            p.setAuthorised(personUpdate.isAuthorised());
            p.setEnabled(personUpdate.isEnabled());
            p.setColours(personUpdate.getColours());
            peopleRepository.save(p);
            return new ResponseEntity(p, HttpStatus.OK);  
        }
        else{
          return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);
        }
    }
    
        @PutMapping
    public ResponseEntity<Person> addPerson(String firstName,String lastName, boolean authorised, boolean enabled) {
        Person p = new Person();
        p.setId(Long.MIN_VALUE);
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAuthorised(authorised);
        p.setEnabled(enabled);
        peopleRepository.save(p);
        return new ResponseEntity(p, HttpStatus.OK);  
    }

}
