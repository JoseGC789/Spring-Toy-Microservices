package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.services.CRService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("/form/companies/owners")
public class OwnerController{
    private final CRService<PersonDto, Long> ownerService;

    public OwnerController(@Qualifier("owner") CRService<PersonDto, Long> ownerService){
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getOwner(@PathVariable final Long id){
        return ResponseEntity.ok(ownerService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllOwners(){
        return ResponseEntity.ok(ownerService.readAll());
    }
}
