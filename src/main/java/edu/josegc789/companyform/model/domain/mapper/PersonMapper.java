package edu.josegc789.companyform.model.domain.mapper;

import edu.josegc789.companyform.model.domain.dtos.PersonDto;
import edu.josegc789.companyform.model.domain.entities.Employee;
import edu.josegc789.companyform.model.domain.entities.Owner;
import edu.josegc789.companyform.model.domain.entities.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper{

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PersonDto personToDto(Person entity);

    @InheritInverseConfiguration
    Owner dtoToOwner(PersonDto dto);

    @InheritInverseConfiguration
    Employee dtoToEmployee(PersonDto dto);
}
