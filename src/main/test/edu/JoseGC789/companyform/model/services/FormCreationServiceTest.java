package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import edu.JoseGC789.companyform.model.domain.entities.Owner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Collections;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

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
        company = new Company(1L, "company_name", new Owner(), Collections.emptyList());
        company.getOwner().setId(1L);
        company.getOwner().setName("Owner name");
        CompanyDto.CompanyDtoBuilder builder = CompanyDto.builder();
        expected = builder
                .id(company.getId())
                .name(company.getName())
                .owner(new PersonDto(company.getOwner()))
                .employees(company.getEmployees().stream().map(PersonDto::new).collect(Collectors.toList()))
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