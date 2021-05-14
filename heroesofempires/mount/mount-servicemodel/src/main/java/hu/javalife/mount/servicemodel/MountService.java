/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.mount.servicemodel;

import hu.javalife.heroesofempires.mount.daomodel.Mount;
import hu.javalife.heroesofempires.mount.daomodel.MountException;
import java.util.List;

/**
 *
 * @author Roolli
 */
public interface MountService {

    public long getNumberofMounts();

    public Mount add(Mount newMount) throws MountException;

    public List<Mount> getAll();

    public Mount getById(long mountId) throws MountException;
    public Mount getByName(String mountName) throws MountException;
    public Mount modify(long mId,Mount editedMount) throws MountException;
    public void delete(long mId) throws MountException;
    public List<Mount> search(int start,int count, Mount predicate, String sortField,String sortDirection);            
}
