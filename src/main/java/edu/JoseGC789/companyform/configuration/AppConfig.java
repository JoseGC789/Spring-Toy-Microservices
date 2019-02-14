package edu.JoseGC789.companyform.configuration;

import edu.JoseGC789.companyform.model.domain.dtos.CompanyDto;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{

    @Bean
    public DozerBeanMapper getMapper(){
        return new DozerBeanMapper();
    }

    @Bean
    public CompanyDto.CompanyDtoBuilder getCompanyDtoBuilder(){
        return CompanyDto.builder();
    }
}
