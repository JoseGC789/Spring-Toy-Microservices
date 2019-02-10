package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.dtos.CompanyDTO;
import edu.JoseGC789.companyform.entities.Company;
import edu.JoseGC789.companyform.model.interfaces.CRUDService;
import edu.JoseGC789.companyform.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import static edu.JoseGC789.companyform.utils.Transformer.toEntity;

@Service
public class CompanyCRUDService implements CRUDService<Long,CompanyDTO>{
    private final CompanyRepository companyRepository;

    public CompanyCRUDService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDTO create(final CompanyDTO companyDTO){
        final Company company = companyRepository.save(toEntity(companyDTO, Company::new));
        return new CompanyDTO(company);
    }

    @Override
    public CompanyDTO read(Long id){
        return null;
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO){
        return null;
    }

    @Override
    public CompanyDTO delete(Long t){
        return null;
    }
}
