package dmit2015.hr.view;

import dmit2015.hr.entity.Country;
import dmit2015.hr.repository.CountryRepository;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentCountryListController")
@ViewScoped
public class CountryListController implements Serializable {

    @Inject
    private CountryRepository _countryRepository;

    @Getter
    private List<Country> countryList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            countryList = _countryRepository.findAll();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}