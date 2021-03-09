package dmit2015.hr.repository;

import dmit2015.hr.entity.Location;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class LocationRepository extends AbstractJpaRepository<Location, Long> {

    public LocationRepository() {
        super(Location.class);
    }
}
