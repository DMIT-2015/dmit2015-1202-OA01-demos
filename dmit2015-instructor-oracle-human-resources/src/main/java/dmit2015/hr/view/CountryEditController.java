package dmit2015.hr.view;

import dmit2015.hr.entity.Country;
import dmit2015.hr.entity.Region;
import dmit2015.hr.repository.CountryRepository;
import dmit2015.hr.repository.RegionRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentCountryEditController")
@ViewScoped
public class CountryEditController implements Serializable {

    @Inject
    private CountryRepository _countryRepository;

    @Inject
    private RegionRepository _regionRepository;

    @Getter @Setter
    private Long selectedRegionId;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private String editId;

    @Getter
    private Country existingCountry;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            Optional<Country> optionalEntity = _countryRepository.findOneById(editId);
            optionalEntity.ifPresent(entity -> {
                existingCountry = entity;
                selectedRegionId = existingCountry.getRegion().getRegionId();
            });
        }
    }

    public String onUpdate() {
        String nextPage = "";
        try {

            Region selectedRegion = _regionRepository.find(selectedRegionId);
            existingCountry.setRegion(selectedRegion);

            _countryRepository.update(existingCountry);
            Messages.addFlashGlobalInfo("Update was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Update was not successful.");
        }
        return nextPage;
    }

    public String onDelete() {
        String nextPage = "";
        try {
            _countryRepository.delete(existingCountry.getCountryId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}