package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.services.CRService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyFormController{
    private final CRService<Long, CompanyDto> service;

    public CompanyFormController(CRService<Long, CompanyDto> service){
        this.service = service;
    }

    @PostMapping("/asd")
    public ResponseEntity<CompanyDto> get(@RequestBody CompanyDto companyBody){
        return ResponseEntity.ok(service.create(companyBody));
    }
}
