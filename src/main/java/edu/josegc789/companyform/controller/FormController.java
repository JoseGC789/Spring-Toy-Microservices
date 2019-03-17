package edu.josegc789.companyform.controller;

import edu.josegc789.companyform.model.domain.dtos.CompanyDto;
import edu.josegc789.companyform.model.services.CreationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@Validated
public class FormController{
    private final CreationService<CompanyDto> formService;

    public FormController(@Qualifier("form") CreationService<CompanyDto> formService){
        this.formService = formService;
    }

    @PostMapping("/form")
    @ApiOperation(
            value = "Returns all persisted companies",
            notes = "Given CompanyDto"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not a valid body."),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CompanyDto> postCompany(@Valid @RequestBody final CompanyDto companyBody){
        return ResponseEntity.ok(formService.create(companyBody));
    }
}
