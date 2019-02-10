package edu.JoseGC789.companyform.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import edu.JoseGC789.companyform.entities.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import static edu.JoseGC789.companyform.entities.Person.Role.OWNER;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonDeserialize(builder = OwnerDTO.OwnerDTOBuilder.class)
public final class OwnerDTO{
    private Long id;

    @Size(max = 100)
    @NotEmpty
    private final String name;

    private final Person.Role role = OWNER;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OwnerDTOBuilder{

    }
}
