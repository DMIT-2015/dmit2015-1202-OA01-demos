package dmit2015.hr.repository;

import dmit2015.hr.entity.Employee;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EmployeeRepository extends AbstractJpaRepository<Employee, Long> {

    public EmployeeRepository() {
        super(Employee.class);
    }
}
