package edu.JoseGC789.companyform.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import edu.JoseGC789.companyform.entities.Company;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonDeserialize(builder = CompanyDTO.CompanyDTOBuilder.class)
public final class CompanyDTO{
    private Long id;

    @NotEmpty
    private final String name;

    @NotNull
    private final OwnerDTO owner;

    @NotNull
    private final Set<ClientDTO> clients;

    public CompanyDTO(Company company){
        this.id = company.getId();
        this.name = company.getName();
        this.owner = null;
        this.clients = null;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class CompanyDTOBuilder{

    }
}
