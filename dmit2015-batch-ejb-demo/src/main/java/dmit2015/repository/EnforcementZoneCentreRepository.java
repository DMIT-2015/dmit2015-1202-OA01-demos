package dmit2015.repository;

import dmit2015.entity.EnforcementZoneCentre;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class EnforcementZoneCentreRepository extends AbstractJpaRepository<EnforcementZoneCentre, Short> {

    public EnforcementZoneCentreRepository() {
        super(EnforcementZoneCentre.class);
    }

    public List<EnforcementZoneCentre> findManyWithinDistance(double longitude, double latitude, double distance) {
        Point<G2D> pointLocation = DSL.point(CoordinateReferenceSystems.WGS84, DSL.g(longitude, latitude));

        String jpql = "SELECT e FROM EnforcementZoneCentre e WHERE distance(e.geoLocation, :pointLocationValue) <= :distanceValue";
        TypedQuery<EnforcementZoneCentre> query = getEntityManager().createQuery(jpql, EnforcementZoneCentre.class);
        query.setParameter("pointLocationValue", pointLocation);
        query.setParameter("distanceValue", distance);
        List<EnforcementZoneCentre> queryResultList = query.getResultList();
        return queryResultList;
    }

    public Optional<EnforcementZoneCentre> findOneWithinDistance(double longitude, double latitude, double distance) {
        Optional<EnforcementZoneCentre> optionalQueryResult = Optional.empty();

        List<EnforcementZoneCentre> queryResultList = findManyWithinDistance(longitude, latitude, distance);
        if (queryResultList.size() > 0) {
            EnforcementZoneCentre firstEnforcementZoneCentre = queryResultList.get(0);
            optionalQueryResult = Optional.of(firstEnforcementZoneCentre);
        }

        return optionalQueryResult;
    }
}