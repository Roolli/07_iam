/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.heroesofempires.stable.servicemodel;

import javax.persistence.Id;

/**
 *
 * @author Roolli
 */
public interface StableService {
   
   public void ProcessStableAction(String token, long heroId,long mountId);
    
}
