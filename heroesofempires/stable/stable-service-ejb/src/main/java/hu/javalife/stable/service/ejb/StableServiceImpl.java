/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.stable.service.ejb;

import hu.javalife.heroesofempires.hero.datamodel.Hero;
import hu.javalife.heroesofempires.mount.daomodel.Mount;
import hu.javalife.heroesofempires.stable.servicemodel.StableException;
import hu.javalife.heroesofempires.stable.servicemodel.StableService;
import javax.ejb.Stateless;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Roolli
 */
@Stateless
public class StableServiceImpl implements StableService {

    private final String heroAddress;
    private final String heroPort;
    private final String mountAddress;
    private final String mountPort;

    public StableServiceImpl() {
        this.heroAddress = System.getenv("HERO_ADDRESS") != null ? System.getenv("HERO_ADDRESS") : "localhost";
        this.heroPort = System.getenv("HERO_PORT") != null ? System.getenv("HERO_PORT") : "8081";
        this.mountAddress  = System.getenv("MOUNT_ADDRESS") != null ? System.getenv("MOUNT_ADRESS") : "localhost";
        this.mountPort = System.getenv("MOUNT_PORT") != null ? System.getenv("MOUNT_PORT") : "localhost";
    }
    
   
    
    private Hero getHero(String token,long id) {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://" +this.heroAddress + ":" + this.heroPort+"/").path("hero/" + id);
        
        try {
            return target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", token).get(Hero.class);
        }
        catch(WebApplicationException webEx)
        {
            switch(webEx.getResponse().getStatus())
            {
                default:
                    throw new StableException();
            }
        }
    }
    private Mount getMount(String token,long id) {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://" +this.mountAddress + ":" + this.mountPort+"/").path("mount/" + id);
        
        try {
            return target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", token).get(Mount.class);
        }
        catch(WebApplicationException webEx)
        {
            switch(webEx.getResponse().getStatus())
            {
                default:
                    throw new StableException();
            }
        }
    }

    @Override
    public void ProcessStableAction(String token, long heroId, long mountId) {
        Hero h = getHero(token, heroId);
        Mount m = getMount(token,mountId);
        
    }
}
