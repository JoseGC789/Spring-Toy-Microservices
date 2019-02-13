package edu.JoseGC789.companyform.model.services;

import edu.JoseGC789.companyform.model.domain.dtos.PersonDto;
import edu.JoseGC789.companyform.model.domain.entities.Owner;
import edu.JoseGC789.companyform.model.helpers.DtoConverter;
import edu.JoseGC789.companyform.model.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Qualifier("owner")
public final class OwnerCRService implements CRService<PersonDto, Long>{
    private final OwnerRepository ownerRepository;
    private final DtoConverter convert;

    public OwnerCRService(OwnerRepository ownerRepository, DtoConverter convert){
        this.ownerRepository = ownerRepository;
        this.convert = convert;
    }

    @Override
    public PersonDto create(final PersonDto personDto){
        final Owner owner = ownerRepository.save(convert.toEntity(personDto, Owner::new));
        return new PersonDto(owner);
    }

    @Override
    public PersonDto read(final Long id){
        return ownerRepository.findById(id)
                .map(PersonDto::new)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Owner doesn't exists"));
    }

    @Override
    public List<PersonDto> readAll(){
        return ownerRepository.findAll().stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());
    }
}
