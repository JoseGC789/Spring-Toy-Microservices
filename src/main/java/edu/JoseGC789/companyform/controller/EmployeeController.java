package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.services.CRService;
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
    public ResponseEntity<PersonDto> getEmployee(@PathVariable final Long id){
        return ResponseEntity.ok(employeeService.read(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.readAll());
    }
}
