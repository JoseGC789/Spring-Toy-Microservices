package edu.JoseGC789.companyform.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonDeserialize(builder = CompanyDTO.CompanyDTOBuilder.class)
public final class CompanyDTO{
    @Setter
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CompanyDTOBuilder{

    }
}
