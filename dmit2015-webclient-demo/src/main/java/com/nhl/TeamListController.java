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

@Named("currentTeamListController")
@ViewScoped
public class TeamListController implements Serializable {

    @Inject
    @RestClient
    private NhlApiService _nhlApiService;

    @Getter
    private List<Team> teamList;

    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            teamList = _nhlApiService.findAllTeams().getTeams();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}