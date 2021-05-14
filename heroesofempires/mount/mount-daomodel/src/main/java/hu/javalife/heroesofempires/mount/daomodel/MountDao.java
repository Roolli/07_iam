/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.heroesofempires.mount.daomodel;

import java.util.List;

/**
 *
 * @author Roolli
 */
public interface MountDao {

    public Mount modify(long id, Mount edited);

    public Mount getById(long id);

    public Mount getByName(String id);

    public boolean isNameAvailable(String name);

    public List<Mount> getAll();

    public long getItemCount();

    public Mount create(Mount newData);

    public void delete(long id);

    public List<Mount> get(int start, int count, Mount searchParam, String shortField, String shortDir);
}
