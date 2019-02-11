package edu.JoseGC789.companyform.model.domain.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import edu.JoseGC789.companyform.model.domain.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = PRIVATE)
@Builder
@JsonDeserialize(builder = CompanyDto.CompanyDtoBuilder.class)
public final class CompanyDto{
    private Long id;

    @NotEmpty
    @NotNull
    @Size(max = 100)
    private final String name;

    public CompanyDto(Company company){
        this.id = company.getId();
        this.name = company.getName();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CompanyDtoBuilder{

    }
}
