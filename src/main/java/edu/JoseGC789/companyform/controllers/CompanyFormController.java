package edu.JoseGC789.companyform.controllers;

import edu.JoseGC789.companyform.dtos.CompanyDTO;
import edu.JoseGC789.companyform.repositories.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyFormController{

    private final PersonRepository personRepository;

    public CompanyFormController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @PostMapping("/asd")
    public ResponseEntity<CompanyDTO> get(@RequestBody CompanyDTO companyBody){
        return ResponseEntity.ok(companyBody);
    }
}
