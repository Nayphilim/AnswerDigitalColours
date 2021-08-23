package com.answerdigital.colourstest.controller;

import com.answerdigital.colourstest.model.Colour;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.ColoursRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Colours")
public class ColoursController {

    @Autowired
    private ColoursRepository coloursRepository;

    @GetMapping
    public ResponseEntity<List<Colour>> getColours() {
        return new ResponseEntity(coloursRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Colour> getColour(@PathVariable("id") long id) {
                Optional<Colour> c = coloursRepository.findById(id);
        if(!c.isEmpty()){
          return new ResponseEntity(c, HttpStatus.OK);  
        }
        else{
          return new ResponseEntity("Colour not found",HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping
    public ResponseEntity<Colour> addColour(String name) {
        Colour c = new Colour();
        c.setId(Long.MIN_VALUE);
        c.setName(name);
        coloursRepository.save(c);
        return new ResponseEntity(c, HttpStatus.OK);
    }
    
}
