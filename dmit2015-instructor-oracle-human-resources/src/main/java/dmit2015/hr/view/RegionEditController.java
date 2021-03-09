package dmit2015.hr.view;

import dmit2015.hr.entity.Region;
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

@Named("currentRegionEditController")
@ViewScoped
public class RegionEditController implements Serializable {

    @Inject
    private RegionRepository _regionRepository;

    @Inject
    @ManagedProperty("#{param.editId}")
    @Getter
    @Setter
    private Long editId;

    @Getter
    private Region existingRegion;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            Optional<Region> optionalEntity = _regionRepository.findOneById(editId);
            optionalEntity.ifPresent(entity -> existingRegion = entity);
        }
    }

    public String onUpdate() {
        String nextPage = "";
        try {
            _regionRepository.update(existingRegion);
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
            _regionRepository.deleteById(existingRegion.getRegionId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}