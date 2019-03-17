package edu.josegc789.companyform.model.domain.mapper;

import edu.josegc789.companyform.model.domain.dtos.CompanyDto;
import edu.josegc789.companyform.model.domain.entities.Company;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface CompanyMapper{

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "employees", source = "employees")
    CompanyDto companyToDto(Company entity);

    @InheritInverseConfiguration
    Company dtoToCompany(CompanyDto dto);
}
