package dmit2015.hr.repository;

import dmit2015.hr.entity.JobsEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class JobRepository {

    @PersistenceContext//(unitName = "oracle-hr-jpa-pu")
    private EntityManager entityManager;

    public void create(JobsEntity newJob) {
        entityManager.persist(newJob);
    }

    //public void update(String id, JobsEntity )


}
