package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
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

@RestController
public class CompanyController{
    private final CRService<CompanyDto, Long> companyService;

    public CompanyController(@Qualifier("company") CRService<CompanyDto, Long> companyService){
        this.companyService = companyService;
    }


    @GetMapping("/form/companies/{id}")
    @ApiOperation(
            value = "Returns a single persisted company",
            notes = "Given company id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not a valid param."),
            @ApiResponse(code = 404, message = "Company doesn't exist"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CompanyDto> getCompany(@PathVariable final Long id){
        return ResponseEntity.ok(companyService.read(id));
    }

    @GetMapping("/form/companies")
    @ApiOperation(
            value = "Returns all persisted companies",
            notes = "No arguments"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<CompanyDto>> getAllCompanies(){
        return ResponseEntity.ok(companyService.readAll());
    }
}
