package hu.javalife.stable.rest;

import hu.javalife.heroesofempires.stable.servicemodel.MountIsInStableException;
import hu.javalife.heroesofempires.stable.servicemodel.MountIsNotInStableException;
import hu.javalife.heroesofempires.stable.servicemodel.StableException;
import hu.javalife.heroesofempires.stable.servicemodel.StableIsFullException;
import hu.javalife.heroesofempires.stable.servicemodel.StableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roolli
 */
@Path("stable")
@ApplicationScoped
@Api(value="/stable",consumes = "application/json")
public class StableResource {
        
    @Inject
    StableService service;
    
     @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "Store Mount",
            notes = "Stores a mount",
            consumes = "application/x-www-form-urlencoded")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Hero or mount not found!"),
        @ApiResponse(code = 500, message = "Unexpected Error occured")})
    public Response storeMount(
            @Context SecurityContext sc,
            @HeaderParam("Authorization") String token,
            @ApiParam(value = "Id of hero", required = true) @FormParam("heroId") long heroId,
            @ApiParam(value = "Id of mount", required = true) @FormParam("mountId") long mountId) {
        
        try {
            service.StoreMount(token, heroId, mountId);
        }  catch(StableIsFullException  e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch(MountIsInStableException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch(StableException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        return Response.status(200).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "Takes a mount",
            notes = "Takes out a mount",
            consumes = "application/x-www-form-urlencoded")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Hero or mount not found"),
        @ApiResponse(code = 500, message = "Unexpected error occured")})
    public Response takeMount(
            @Context SecurityContext sc,
            @HeaderParam("Authorization") String token,
            @ApiParam(value = "Id of hero", required = true) @FormParam("heroId") long heroId,
            @ApiParam(value = "Id of mount", required = true) @FormParam("mountId") long mountId) {
        
        try {
            service.TakeMount(token, heroId, mountId);
        } catch(MountIsNotInStableException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch(StableException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(200).build();
    }
}
