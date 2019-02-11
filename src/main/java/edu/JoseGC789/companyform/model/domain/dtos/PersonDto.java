package edu.JoseGC789.companyform.model.domain.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import edu.JoseGC789.companyform.model.domain.entities.Person;
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
@JsonDeserialize(builder = PersonDto.PersonDtoBuilder.class)
public final class PersonDto{
    private Long id;

    @Size(max = 100)
    @NotEmpty
    @NotNull
    private final String name;

    @NotNull
    private final Person.Role role;

    @NotNull
    private final CompanyDto business;

    public PersonDto(Person person){
        this.id = person.getId();
        this.name = person.getName();
        this.role = person.getRole();
        this.business = new CompanyDto(person.getBusiness());

    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonDtoBuilder{

    }
}
