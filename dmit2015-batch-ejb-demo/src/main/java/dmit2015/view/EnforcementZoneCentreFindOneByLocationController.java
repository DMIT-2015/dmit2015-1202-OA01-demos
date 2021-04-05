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
import java.util.Optional;

@Named("currentEnforcementZoneCentreFindOneByLocationController")
@ViewScoped
public class EnforcementZoneCentreFindOneByLocationController implements Serializable {

    @Inject
    private EnforcementZoneCentreRepository _enforcementzonecentreRepository;

    @Getter
    private EnforcementZoneCentre findOneResult;

    @Getter @Setter
    private double longitude = -113.503519;

    @Getter @Setter
    private double latitude = 53.5678765;

    @Getter @Setter
    private double distance = 500;     // distance in meters

    public void onSearch() {
        try {
            Optional<EnforcementZoneCentre> optionalEnforcementZoneCentre = _enforcementzonecentreRepository.findOneWithinDistance(longitude, latitude, distance);
            if (optionalEnforcementZoneCentre.isPresent()) {
                findOneResult = optionalEnforcementZoneCentre.get();
            } else {
                findOneResult = null;
            }
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }

    public void onClear() {
        findOneResult = null;
    }
}