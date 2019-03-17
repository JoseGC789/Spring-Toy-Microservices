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
import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = PRIVATE)
@Builder
@JsonDeserialize(builder = PersonDto.PersonDtoBuilder.class)
public class PersonDto{
    @JsonIgnore
    @Getter(onMethod = @__(@JsonProperty))
    private final Long id;

    @Size(max = 100)
    @NotEmpty
    @NotNull
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PersonDtoBuilder{

    }
}
