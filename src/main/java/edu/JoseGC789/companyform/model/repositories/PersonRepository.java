package edu.JoseGC789.companyform.model.repositories;

import edu.JoseGC789.companyform.model.domain.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{
}
