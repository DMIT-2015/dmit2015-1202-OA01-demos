package dmit2015.hr.repository;

import dmit2015.hr.entity.Job;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class JobRepository extends AbstractJpaRepository<Job, String> {

    public JobRepository() {
        super(Job.class);
    }
}
