package edu.JoseGC789.companyform.configuration;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig{

    @Bean
    public Mapper getMapper(){
        return Dozer.INSTANCE.getMapper();
    }

    public enum Dozer{
        INSTANCE(DozerBeanMapperBuilder.buildDefault());

        private final Mapper mapper;

        Dozer(Mapper mapper){
            this.mapper = mapper;
        }

        public Mapper getMapper(){
            return mapper;
        }
    }

}
