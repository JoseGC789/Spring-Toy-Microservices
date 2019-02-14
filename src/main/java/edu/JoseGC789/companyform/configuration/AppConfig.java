package edu.JoseGC789.companyform.configuration;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{

    @Bean
    public DozerBeanMapper getMapper(){
        return new DozerBeanMapper();
    }

}
