package dmit2015.hr.view;

import dmit2015.hr.entity.Country;
import dmit2015.hr.entity.Region;
import dmit2015.hr.repository.CountryRepository;
import dmit2015.hr.repository.RegionRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("currentCountryCreateController")
@RequestScoped
public class CountryCreateController {

    @Inject
    private CountryRepository _countryRepository;

    @Inject
    private RegionRepository _regionRepository;

    @Getter
    private Country newCountry = new Country();

    @Getter @Setter
    private Long selectedRegionId;

    public String onCreate() {
        String nextPage = "";
        try {

            Region selectedRegion = _regionRepository.find(selectedRegionId);
            newCountry.setRegion(selectedRegion);

            _countryRepository.create(newCountry);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}