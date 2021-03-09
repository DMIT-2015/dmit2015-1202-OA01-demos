package dmit2015.hr.repository;

import dmit2015.hr.entity.JobHistory;
import dmit2015.hr.entity.JobHistoryPK;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class JobHistoryRepository extends AbstractJpaRepository<JobHistory, JobHistoryPK> {

    public JobHistoryRepository() {
        super(JobHistory.class);
    }

}
