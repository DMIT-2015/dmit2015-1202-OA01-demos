package dmit2015.view;

import dmit2015.entity.EnforcementZoneCentre;
import dmit2015.repository.EnforcementZoneCentreRepository;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentEnforcementZoneCentreListController")
@ViewScoped
public class EnforcementZoneCentreListController implements Serializable {

    @Inject
    private EnforcementZoneCentreRepository _enforcementzonecentreRepository;

    @Getter
    private List<EnforcementZoneCentre> enforcementzonecentreList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            enforcementzonecentreList = _enforcementzonecentreRepository.list();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}