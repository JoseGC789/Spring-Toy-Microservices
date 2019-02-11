package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import edu.JoseGC789.companyform.model.repositories.CompanyRepository;
import edu.JoseGC789.companyform.model.helpers.DtoConverter;
import org.springframework.stereotype.Service;

@Service
public final class CompanyCRService implements CRService<Long, CompanyDto>{
    private final CompanyRepository companyRepository;
    private final DtoConverter convert;

    public CompanyCRService(CompanyRepository companyRepository, DtoConverter convert){
        this.companyRepository = companyRepository;
        this.convert = convert;
    }

    @Override
    public CompanyDto create(final CompanyDto companyDTO){
        final Company company = companyRepository.save(convert.toEntity(companyDTO, Company::new));
        return new CompanyDto(company);
    }

    @Override
    public CompanyDto read(Long id){
        return null;
    }
}
