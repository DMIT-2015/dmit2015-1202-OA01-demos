package dmit2015.hr.repository;

import dmit2015.hr.entity.Country;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CountryRepository extends AbstractJpaRepository<Country, String> {

    public CountryRepository() {
        super(Country.class);
    }

}
