package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import edu.JoseGC789.companyform.model.domain.entities.Owner;
import edu.JoseGC789.companyform.model.repositories.CompanyRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(MockitoJUnitRunner.class)
public class CompanyCRServiceTest{

    @Mock
    private DozerBeanMapper dozerBeanMapper;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyCRService companyCRService;

    private Company company;
    private CompanyDto expected;
    private List<CompanyDto> expectedList;

    @Before
    public void setup(){
        company = new Company(1L,"company_name", new Owner(), Collections.emptyList());
        company.getOwner().setId(1L);
        company.getOwner().setName("Owner name");
        CompanyDto.CompanyDtoBuilder builder = CompanyDto.builder();
        expected = builder
                .id(company.getId())
                .name(company.getName())
                .owner(new PersonDto(company.getOwner()))
                .employees(company.getEmployees().stream().map(PersonDto::new).collect(Collectors.toList()))
                .build();
        expectedList = Arrays.asList(expected, expected, expected);
    }

    @Test
    public void testShouldReturnNewlyCreatedCompanyDto(){
        Mockito.when(dozerBeanMapper.map(anyObject(),any())).thenReturn(company);
        Mockito.when(companyRepository.save(any())).thenReturn(company);

        CompanyDto created = companyCRService.create(expected);

        assertEquals("Expected should equals created CompanyDto", expected, created);
    }

    @Test(expected = ResponseStatusException.class)
    public void testShouldThrowResponseExceptionWhenNotFound(){
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.empty());

        companyCRService.read(5L);
    }

    @Test
    public void testShouldReturnCompanyDtoFoundInRepo(){
        Mockito.when(companyRepository.findById(any())).thenReturn(Optional.of(company));

        CompanyDto found = companyCRService.read(5L);

        assertEquals("Expected should equals found CompanyDto",expected,found);
        assertNotNull("Found mustn't be null", found);
    }

    @Test
    public void testShouldReturListOfCompanyDtoFoundInRepo(){
        Mockito.when(companyRepository.findAll()).thenReturn(Arrays.asList(company,company,company));

        List<CompanyDto> foundList = companyCRService.readAll();

        assertEquals("Expected list should equals found list of CompanyDtos",expectedList,foundList);
        assertNotNull("Found list mustn't be null", foundList);
    }
}