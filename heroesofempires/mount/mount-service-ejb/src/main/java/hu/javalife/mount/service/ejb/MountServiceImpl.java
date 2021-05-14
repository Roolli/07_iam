/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.mount.service.ejb;

import hu.javalife.heroesofempires.mount.daomodel.Mount;
import hu.javalife.heroesofempires.mount.daomodel.MountDao;
import hu.javalife.heroesofempires.mount.daomodel.MountException;
import hu.javalife.heroesofempires.mount.daomodel.MountNameTakenException;
import hu.javalife.mount.servicemodel.MountService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Roolli
 */
@Stateless
public class MountServiceImpl implements MountService {

    @Inject
    MountDao dao;

    @Override
    public long getNumberofMounts() {
        return dao.getItemCount();
    }

    @Override
    public Mount add(Mount newMount) throws MountException {
        if (dao.isNameAvailable(newMount.getName())) {
            return dao.create(newMount);
        }
        throw new MountNameTakenException();
    }

    @Override
    public List<Mount> getAll() {
        return dao.getAll();
    }

    @Override
    public Mount getById(long mountId) throws MountException {
        Mount m = dao.getById(mountId);
        if (m == null) {
            throw new MountException();
        }

        return m;
    }

    @Override
    public Mount getByName(String mountName) throws MountException {
        Mount m = dao.getByName(mountName);
        if (m == null) {
            throw new MountException();
        } else {
            return m;
        }
    }

    @Override
    public Mount modify(long mId, Mount editedMount) throws MountException {
        Mount m = dao.getById(mId);
        if (m != null && dao.isNameAvailable(m.getName())) {
            return dao.modify(mId, editedMount);
        }
        throw new MountException();
    }

    @Override
    public void delete(long mId) throws MountException {

        if (dao.getById(mId) == null) {
            throw new MountException();
        }
        dao.delete(mId);
    }

    @Override
    public List<Mount> search(int start, int count, Mount predicate, String sortField, String sortDirection) {
        return dao.get(start, count, predicate, sortField, sortField);
    }

}
