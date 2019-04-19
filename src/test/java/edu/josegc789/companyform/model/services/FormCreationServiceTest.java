package edu.josegc789.companyform.model.services;

import edu.josegc789.companyform.model.domain.mapper.PersonMapper;
import edu.josegc789.companyform.model.domain.dtos.CompanyDto;
import edu.josegc789.companyform.model.domain.entities.Company;
import edu.josegc789.companyform.model.domain.entities.Owner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Collections;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FormCreationServiceTest{

    @Mock
    private CompanyCRService companyCRService;

    @InjectMocks
    private FormCreationService formCreationService;

    private Company company;
    private CompanyDto expected;

    @Before
    public void setup(){
        PersonMapper mapper = PersonMapper.INSTANCE;
        company = new Company(1L, "company_name", new Owner(), Collections.emptyList());
        company.getOwner().setId(1L);
        company.getOwner().setName("Owner name");
        CompanyDto.CompanyDtoBuilder builder = CompanyDto.builder();
        expected = builder
                .id(company.getId())
                .name(company.getName())
                .owner(mapper.personToDto(company.getOwner()))
                .employees(company.getEmployees().stream().map(mapper::personToDto).collect(Collectors.toList()))
                .build();
    }

    @Test
    public void testShouldReturnCreatedCompanyDto(){
        Mockito.when(companyCRService.create(any())).thenReturn(expected);

        CompanyDto created = formCreationService.create(expected);

        assertEquals("Expected should equals created CompanyDto",expected,created);
        assertNotNull("Created mustn't be null", created);
    }

}