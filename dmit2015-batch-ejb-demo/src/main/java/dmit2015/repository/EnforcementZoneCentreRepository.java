package dmit2015.repository;

import dmit2015.entity.EnforcementZoneCentre;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EnforcementZoneCentreRepository extends AbstractJpaRepository<EnforcementZoneCentre, Short> {

    public EnforcementZoneCentreRepository() {
        super(EnforcementZoneCentre.class);
    }

}