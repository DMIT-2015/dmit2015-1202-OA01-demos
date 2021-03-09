package dmit2015.hr.view;

import dmit2015.hr.entity.Region;
import dmit2015.hr.repository.RegionRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("currentRegionCreateController")
@RequestScoped
public class RegionCreateController {

    @Inject
    private RegionRepository _regionRepository;

    @Getter
    private Region newRegion = new Region();

    public String onCreate() {
        String nextPage = "";
        try {
            _regionRepository.create(newRegion);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}