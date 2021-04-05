package dmit2015.view;

import dmit2015.entity.EnforcementZoneCentre;
import dmit2015.repository.EnforcementZoneCentreRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentEnforcementZoneCentreFindManyByLocationController")
@ViewScoped
public class EnforcementZoneCentreFindManyByLocationController implements Serializable {

    @Inject
    private EnforcementZoneCentreRepository _enforcementzonecentreRepository;

    @Getter
    private List<EnforcementZoneCentre> findManyResultList;

    @Getter @Setter
    private double longitude = -113.503519;

    @Getter @Setter
    private double latitude = 53.5678765;

    @Getter @Setter
    private double distance = 1000;  // distance in meters

    public void onSearch() {
        try {
            findManyResultList = _enforcementzonecentreRepository.findManyWithinDistance(longitude, latitude, distance);
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }

    public void onClear() {
        findManyResultList = null;
    }
}