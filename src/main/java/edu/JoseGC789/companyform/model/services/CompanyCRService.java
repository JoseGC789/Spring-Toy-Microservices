package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import edu.JoseGC789.companyform.model.repositories.CompanyRepository;
import edu.JoseGC789.companyform.model.helpers.DtoConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Qualifier("company")
public final class CompanyCRService implements CRService<CompanyDto, Long>{
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
    public CompanyDto read(final Long id){
         return companyRepository.findById(id)
                 .map(CompanyDto::new)
                 .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Company doesn't exists"));
    }

    @Override
    public List<CompanyDto> readAll(){
        return companyRepository.findAll().stream()
                .map(CompanyDto::new)
                .collect(Collectors.toList());
    }
}
