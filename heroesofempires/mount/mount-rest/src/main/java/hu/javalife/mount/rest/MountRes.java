/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.mount.rest;

import hu.javalife.heroesofempires.mount.daomodel.Mount;
import hu.javalife.heroesofempires.mount.daomodel.MountException;
import hu.javalife.heroesofempires.mount.daomodel.MountNameTakenException;
import hu.javalife.heroesofempires.mount.daomodel.MountType;
import hu.javalife.mount.servicemodel.MountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.SwaggerDefinition;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Roolli
 */
@Path("mount")
@ApplicationScoped
@PermitAll
@SwaggerDefinition(schemes = SwaggerDefinition.Scheme.HTTP)
@Api(value = "/mount", consumes = "application/json")
public class MountRes {

    @Inject
    MountService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all mounts", notes = "List of mounts", response = Mount.class, responseContainer = "List")
    public List<Mount> getAll(@Context SecurityContext ctx) {
        return service.getAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "New Mount", notes = "Creates a mount", consumes = "application/x-www-form-urlencoded", response = Mount.class)
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Mount name unavailable"),
        @ApiResponse(code = 500, message = "unexpected error occured")
    })
    public Response add(@Context SecurityContext ctx, @ApiParam(value = "Name of mount", required = true) @FormParam("name") String mName,
            @ApiParam(value = "Type of mount", required = true) @FormParam("mountType") MountType mType,
            @ApiParam(value = "CarryingCapacity of mount", required = true) @FormParam("carryingCapacity") int capacity,
            @ApiParam(value = "Speed of mount", required = true) @FormParam("speed") int speed,
            @ApiParam(value=  "Hero ID",required=true) @FormParam("heroId") long heroId
    ) {
        Mount m = new Mount();
        m.setCarryingCapacity(capacity);
        m.setMaxSpeed(speed);
        m.setName(mName);
        m.setMountType(mType);
        m.setIsInStable(false);
        m.setHeroId(heroId);
        try {
            return Response.ok(service.add(m)).build();
        } catch (MountException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @RolesAllowed("manage-account")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Mount by Id",
            notes = "Mountby Id",
            response = Mount.class,
            authorizations = {
                @Authorization(value = "Bearer")},
            extensions = {
                @Extension(name = "roles",
                        properties = {
                            @ExtensionProperty(name = "manage-account", value = "getting one mount")}
                )
            }
    )
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "Weapon not found"),
        @ApiResponse(code = 500, message = "some exception")})
    public Response getById(@Context SecurityContext ctx, @ApiParam(value = "ID of mount", required = true) @PathParam("id") @DefaultValue("0") long id) {
        try {
            return Response.ok(service.getById(id)).build();
        } catch (MountException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Mount by name", notes = "Mount by name", response = Mount.class)
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "mount not found"),
        @ApiResponse(code = 500, message = "unexpected error occured")
    })
    public Response getByName(@ApiParam(value = "name of mount", required = true) @QueryParam("name") @DefaultValue("") String mName) {
        try {
            return Response.ok(service.getByName(mName)).build();
        } catch (MountException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @ApiOperation(value = "Mount by id", notes = "Mount by Id",
            consumes = "application/x-www-form-urlencoded",
            response = Mount.class)
    @ApiResponses(
            value = {
                @ApiResponse(code = 400, message = "mount name unavailable"),
                @ApiResponse(code = 404, message = "mount does not exsists"),
                @ApiResponse(code = 500, message = "unexpected error occured")
            })
    public Response updateById(@ApiParam(value = "Id of mount", required = true) @PathParam("id") @DefaultValue("0") long mId,
            @ApiParam(value = "new name of mount", required = true) @PathParam("name") @DefaultValue("") String name,
            @ApiParam(value = "new type of mount", required = true) @PathParam("mountType") MountType type,
            @ApiParam(value = "new capacity of mount", required = true) @PathParam("capacity") int capacity,
            @ApiParam(value = "new speed of mount", required = true) @PathParam("capacity") int speed
    ) {
        try {
            Mount m = new Mount();
            m.setCarryingCapacity(capacity);
            m.setMaxSpeed(speed);
            m.setName(name);
            m.setMountType(type);
            return Response.ok(service.modify(mId, m)).build();
        } catch (MountException e) {
            if (e instanceof MountNameTakenException) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Throwable e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Remove mount by Id", notes = "Removes mount by id")
    @ApiResponses(value = {
    @ApiResponse(code = 404, message = "Mount not found"),
    @ApiResponse(code = 500, message = "unexpected error occured")
    })
    public Response deleteById(@ApiParam(value = "ID of mount", required = true) @PathParam("id") @DefaultValue("0") long mId) {
        try
        {
             service.delete(mId);
             return Response.ok().build();
        }
        catch(MountException e)
        {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build();
        }
        catch(Throwable e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
