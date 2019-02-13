package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.services.CreationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/form")
public class FormController{
    private final CreationService<CompanyDto> formService;

    public FormController(@Qualifier("form") CreationService<CompanyDto> formService){
        this.formService = formService;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> postCompany(@RequestBody final CompanyDto companyBody){
        return ResponseEntity.ok(formService.create(companyBody));
    }
}
