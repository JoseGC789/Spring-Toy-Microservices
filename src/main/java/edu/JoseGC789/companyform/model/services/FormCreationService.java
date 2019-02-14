package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("form")
public class FormCreationService implements CreationService<CompanyDto>{
    private final CRService<CompanyDto, Long> companyService;

    public FormCreationService(@Qualifier("company") CRService<CompanyDto, Long> companyService){
        this.companyService = companyService;
    }

    @Transactional
    @Override
    public CompanyDto create(final CompanyDto companyDto){
        return companyService.create(companyDto);
    }

}
