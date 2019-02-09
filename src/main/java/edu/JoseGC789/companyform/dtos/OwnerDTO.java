package edu.JoseGC789.companyform.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import static edu.JoseGC789.companyform.dtos.OwnerDTO.Role.OWNER;

@Getter
@ToString
@EqualsAndHashCode
@Embeddable
@Setter
@AllArgsConstructor
public final class OwnerDTO{
    @Min(0)
    @Setter
    private Long id;

    @JsonIgnore
    private Role role;

    @Size(max = 100)
    private String name;

    public enum Role{
        OWNER
    }
}
