package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.services.CreationService;
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
import static java.util.Objects.requireNonNull;

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
        return ResponseEntity.ok(formService.create(nullIds(companyBody)));
    }

    private static CompanyDto nullIds(CompanyDto companyDto){
        requireNonNull(companyDto);
        companyDto.setId(null);
        companyDto.getOwner().setId(null);
        companyDto.getEmployees().forEach(personDto -> personDto.setId(null));
        return companyDto;
    }
}
