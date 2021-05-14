/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.mount.rest;

import hu.javalife.heroesofempires.mount.daomodel.Mount;
import hu.javalife.mount.servicemodel.MountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Roolli
 */
@Path("mount")
@ApplicationScoped
@PermitAll
@SwaggerDefinition(schemes = SwaggerDefinition.Scheme.HTTP)
@Api(value ="/mount",consumes = "application/json")
public class MountRes {
    
    @Inject
    MountService service;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Get all mounts",notes = "List of mounts",response = Mount.class,responseContainer = "List")
    public List<Mount> getAll(@Context SecurityContext ctx)
    {
        return service.getAll();
    }
    
}
