package edu.josegc789.companyform.controller;

import edu.josegc789.companyform.model.domain.mapper.PersonMapper;
import edu.josegc789.companyform.model.domain.dtos.CompanyDto;
import edu.josegc789.companyform.model.domain.dtos.PersonDto;
import edu.josegc789.companyform.model.domain.entities.Company;
import edu.josegc789.companyform.model.domain.entities.Employee;
import edu.josegc789.companyform.model.domain.entities.Owner;
import edu.josegc789.companyform.model.services.FormCreationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;

@RunWith(MockitoJUnitRunner.class)
public class FormControllerTest{

    @Mock
    private FormCreationService formCreationService;

    @InjectMocks
    private FormController formController;

    private CompanyDto expected;
    private PersonDto spyOwner;
    private CompanyDto spyCompany;
    private List<PersonDto> spyEmployeeList;
    private PersonDto spyEmployee;

    @Before
    public void setup(){
        PersonMapper mapper = PersonMapper.INSTANCE;
        Owner owner = new Owner();
        owner.setId(2L);
        owner.setName("owner name");

        Employee employee = new Employee();
        employee.setId(4L);
        employee.setName("employee abc");

        Company company = new Company(1L, "company_name", owner, Arrays.asList(employee,employee,employee));

        CompanyDto.CompanyDtoBuilder builder = CompanyDto.builder();

        spyOwner = Mockito.spy(mapper.personToDto(owner));
        spyEmployee = Mockito.spy(mapper.personToDto(employee));
        spyEmployeeList = Mockito.spy(new ArrayList<>());
        spyEmployeeList.add(spyEmployee);
        spyEmployeeList.add(spyEmployee);
        spyEmployeeList.add(spyEmployee);
        spyEmployeeList.add(spyEmployee);

        expected = builder
                .id(company.getId())
                .name(company.getName())
                .owner(spyOwner)
                .employees(spyEmployeeList)
                .build();

        spyCompany = Mockito.spy(expected);
    }

    @Test
    public void testShouldReturnOkWithCreatedCompanyDTo(){
        Mockito.when(formCreationService.create(argThat(new NotNullObjects()))).thenReturn(expected);

        ResponseEntity<CompanyDto> receivedStatus = formController.postCompany(spyCompany);

        assertEquals("Expected should equals received response entity",ResponseEntity.ok(expected),receivedStatus);
        assertNotNull("Received status mustn't be null", receivedStatus);
    }

    public static class NotNullObjects implements ArgumentMatcher<CompanyDto>{
        @Override
        public boolean matches(CompanyDto companyDto){
            return companyDto!=null;
        }
    }
}