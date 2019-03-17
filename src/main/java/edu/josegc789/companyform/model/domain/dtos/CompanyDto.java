package edu.josegc789.companyform.model.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = PRIVATE)
@Builder
@JsonDeserialize(builder = CompanyDto.CompanyDtoBuilder.class)
public class CompanyDto{
    @JsonIgnore
    @Getter(onMethod = @__(@JsonProperty))
    private final Long id;

    @NotEmpty
    @NotNull
    @Size(max = 100)
    private final String name;

    @NotNull
    private final PersonDto owner;

    @NotNull
    private final List<PersonDto> employees;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CompanyDtoBuilder{

    }
}
