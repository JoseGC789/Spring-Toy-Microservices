package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.services.CRService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(
            value = "Returns a single persisted owner",
            notes = "Given owner id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not a valid param."),
            @ApiResponse(code = 404, message = "Owner doesn't exist"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<PersonDto> getOwner(@PathVariable final Long id){
        return ResponseEntity.ok(ownerService.read(id));
    }

    @GetMapping
    @ApiOperation(
            value = "Returns all persisted owners",
            notes = "No arguments"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<PersonDto>> getAllOwners(){
        return ResponseEntity.ok(ownerService.readAll());
    }
}
