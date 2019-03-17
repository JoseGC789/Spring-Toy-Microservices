package edu.josegc789.companyform.model.services;

import edu.josegc789.companyform.model.domain.mapper.CompanyMapper;
import edu.josegc789.companyform.model.domain.mapper.PersonMapper;
import edu.josegc789.companyform.model.domain.dtos.CompanyDto;
import edu.josegc789.companyform.model.domain.entities.Company;
import edu.josegc789.companyform.model.domain.entities.Owner;
import edu.josegc789.companyform.model.repositories.CompanyRepository;
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

@RunWith(MockitoJUnitRunner.class)
public class CompanyCRServiceTest{

    @Mock
    private CompanyMapper mapper;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyCRService companyCRService;

    private Company company;
    private CompanyDto expected;
    private List<CompanyDto> expectedList;

    @Before
    public void setup(){
        PersonMapper mapper = PersonMapper.INSTANCE;
        company = new Company(1L,"company_name", new Owner(), Collections.emptyList());
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
    public void testShouldReturnNewlyCreatedCompanyDto(){
        Mockito.when(mapper.dtoToCompany(any())).thenReturn(company);
        Mockito.when(mapper.companyToDto(any())).thenReturn(expected);
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
        Mockito.when(mapper.companyToDto(any())).thenReturn(expected);

        CompanyDto found = companyCRService.read(5L);

        assertEquals("Expected should equals found CompanyDto",expected,found);
        assertNotNull("Found mustn't be null", found);
    }

    @Test
    public void testShouldReturListOfCompanyDtoFoundInRepo(){
        Mockito.when(companyRepository.findAll()).thenReturn(Arrays.asList(company,company,company));
        Mockito.when(mapper.companyToDto(any())).thenReturn(expected);

        List<CompanyDto> foundList = companyCRService.readAll();

        assertEquals("Expected list should equals found list of CompanyDtos",expectedList,foundList);
        assertNotNull("Found list mustn't be null", foundList);
    }
}