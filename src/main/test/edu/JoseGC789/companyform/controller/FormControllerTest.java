package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import edu.JoseGC789.companyform.model.domain.entities.Owner;
import edu.JoseGC789.companyform.model.services.FormCreationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.stream.Collectors;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class FormControllerTest{

    @Mock
    private FormCreationService formCreationService;

    @InjectMocks
    private FormController formController;

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
    public void testShouldReturnOkWithCreatedCompanyDTo(){
        Mockito.when(formCreationService.create(any())).thenReturn(expected);

        ResponseEntity<CompanyDto> receivedStatus = formController.postCompany(expected);

        assertEquals("Expected should equals received response entity",ResponseEntity.ok(expected),receivedStatus);
        assertNotNull("Received status mustn't be null", receivedStatus);
    }
}