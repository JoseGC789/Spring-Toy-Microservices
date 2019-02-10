package edu.JoseGC789.companyform.controllers;

import edu.JoseGC789.companyform.dtos.CompanyDTO;
import edu.JoseGC789.companyform.model.interfaces.CRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyFormController{
    private final CRUDService<Long,CompanyDTO> service;

    public CompanyFormController(CRUDService<Long, CompanyDTO> service){
        this.service = service;
    }

    @PostMapping("/asd")
    public ResponseEntity<CompanyDTO> get(@RequestBody CompanyDTO companyBody){
        return ResponseEntity.ok(service.create(companyBody));
    }
}
