package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.domain.entities.Employee;
import edu.JoseGC789.companyform.model.helpers.DtoConverter;
import edu.JoseGC789.companyform.model.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Qualifier("employee")
public final class EmployeeCRService implements ListCRService<PersonDto,Long>{

    private final EmployeeRepository employeeRepository;
    private final DtoConverter convert;

    public EmployeeCRService(EmployeeRepository employeeRepository, DtoConverter convert){
        this.employeeRepository = employeeRepository;
        this.convert = convert;
    }

    @Override
    public PersonDto create(final PersonDto personDto){
        final Employee employee = employeeRepository.save(convert.toEntity(personDto, Employee::new));
        return new PersonDto(employee);
    }

    @Override
    public Set<PersonDto> createAll(Set<? extends PersonDto> personDtos){
        List<Employee> employees = employeeRepository.saveAll(
                personDtos.stream()
                        .map(p -> convert.toEntity(p, Employee::new))
                        .collect(Collectors.toSet()));
        return employees.stream()
                .map(PersonDto::new)
                .collect(Collectors.toSet());

    }

    @Override
    public PersonDto read(final Long id){
        return employeeRepository.findById(id)
                .map(PersonDto::new)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Employee doesn't exists"));
    }

    @Override
    public List<PersonDto> readAll(){
        return employeeRepository.findAll().stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());
    }
}
