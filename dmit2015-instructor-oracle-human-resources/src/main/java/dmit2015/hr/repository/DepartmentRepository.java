package dmit2015.hr.repository;

import dmit2015.hr.entity.Department;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class DepartmentRepository extends AbstractJpaRepository<Department, Long> {

    public DepartmentRepository() {
        super(Department.class);
    }

}
