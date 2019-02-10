package edu.JoseGC789.companyform.repositories;

import edu.JoseGC789.companyform.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long>{
}
