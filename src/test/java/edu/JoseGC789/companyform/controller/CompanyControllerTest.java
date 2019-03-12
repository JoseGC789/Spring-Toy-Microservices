package edu.JoseGC789.companyform.controller;

import edu.JoseGC789.companyform.model.domain.mapper.PersonMapper;
import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import edu.JoseGC789.companyform.model.domain.entities.Owner;
import edu.JoseGC789.companyform.model.services.CompanyCRService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest{

    @Mock
    private CompanyCRService companyCRService;

    @InjectMocks
    private CompanyController companyController;

    private Company company;
    private CompanyDto expected;
    private List<CompanyDto> expectedList;

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
        expectedList = Arrays.asList(expected, expected, expected);
    }


    @Test
    public void testShouldReturnOkWithFoundCompanyDTo(){
        Mockito.when(companyCRService.read(any())).thenReturn(expected);

        ResponseEntity<CompanyDto> receivedStatus = companyController.getCompany(5L);

        assertEquals("Expected should equals received response entity", ResponseEntity.ok(expected), receivedStatus);
        assertNotNull("Received status mustn't be null", receivedStatus);
    }

    @Test
    public void testShouldReturnOkWithFoundCompanyDtoList(){
        Mockito.when(companyCRService.readAll()).thenReturn(expectedList);

        ResponseEntity<List<CompanyDto>> receivedStatus = companyController.getAllCompanies();

        assertEquals("Expected list should equals received response entity", ResponseEntity.ok(expectedList), receivedStatus);
        assertNotNull("Received status mustn't be null", receivedStatus);
    }

}