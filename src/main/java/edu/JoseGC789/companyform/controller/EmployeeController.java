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

@RestController("/form/companies/employees")
public class EmployeeController{
    private final CRService<PersonDto, Long> employeeService;

    public EmployeeController(@Qualifier("employee") CRService<PersonDto, Long> employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Returns a single persisted employee",
            notes = "Given employee id"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Not a valid param."),
            @ApiResponse(code = 404, message = "Employee doesn't exist"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<PersonDto> getEmployee(@PathVariable final Long id){
        return ResponseEntity.ok(employeeService.read(id));
    }

    @GetMapping
    @ApiOperation(
            value = "Returns all persisted employees",
            notes = "No arguments"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<PersonDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.readAll());
    }
}
