package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("form")
public class FormCreationService implements CreationService<CompanyDto>{
    private final CRService<CompanyDto, Long> companyService;
    private final CRService<PersonDto, Long> ownerService;
    private final ListCRService<PersonDto, Long> employeeService;

    public FormCreationService(@Qualifier("company") CRService<CompanyDto, Long> companyService,
                               @Qualifier("owner") CRService<PersonDto, Long> ownerService,
                               @Qualifier("employee") ListCRService<PersonDto, Long> employeeService){
        this.companyService = companyService;
        this.ownerService = ownerService;
        this.employeeService = employeeService;
    }

    @Transactional
    @Override
    public CompanyDto create(CompanyDto companyDto){
        ownerService.create(companyDto.getOwner());
        employeeService.createAll(companyDto.getEmployees());
        return companyService.create(companyDto);
    }
}
