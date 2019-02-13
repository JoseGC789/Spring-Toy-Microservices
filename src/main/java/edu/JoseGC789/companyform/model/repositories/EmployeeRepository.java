package edu.JoseGC789.companyform.model.repositories;

import edu.JoseGC789.companyform.model.domain.entities.Employee;
import edu.JoseGC789.companyform.model.domain.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
}
