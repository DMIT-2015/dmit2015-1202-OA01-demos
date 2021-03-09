package dmit2015.hr.view;

import dmit2015.hr.entity.Region;
import dmit2015.hr.repository.RegionRepository;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentRegionListController")
@ViewScoped
public class RegionListController implements Serializable {

    @Inject
    private RegionRepository _regionRepository;

    @Getter
    private List<Region> regionList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            regionList = _regionRepository.findAll();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}