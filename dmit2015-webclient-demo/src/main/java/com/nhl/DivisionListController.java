package com.nhl;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.omnifaces.util.Messages;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentDivisionListController")
@ViewScoped
public class DivisionListController implements Serializable {

    @Inject
    @RestClient
    private NhlApiService _nhlApiService;

    @Getter
    private List<Division> divisionList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            divisionList = _nhlApiService.findAllDivision().getDivisions();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}