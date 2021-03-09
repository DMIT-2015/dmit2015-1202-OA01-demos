package dmit2015.hr.repository;

import dmit2015.hr.entity.Region;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class RegionRepository extends AbstractJpaRepository<Region, Long> {

    public RegionRepository() {
        super(Region.class);
    }

}
