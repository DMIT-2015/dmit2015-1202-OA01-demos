package com.nhl;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient(baseUri = "https://statsapi.web.nhl.com/api/v1")
public interface NhlApiService {

    @GET
    @Path("/divisions")
    DivisionResponse findAllDivision();

    @GET
    @Path("/teams")
    TeamResponse findAllTeams();
}
