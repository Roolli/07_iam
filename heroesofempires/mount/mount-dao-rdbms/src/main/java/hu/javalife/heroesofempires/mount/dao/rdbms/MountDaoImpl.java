/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.javalife.heroesofempires.mount.dao.rdbms;

import hu.javalife.heroesofempires.mount.daomodel.Mount;
import hu.javalife.heroesofempires.mount.daomodel.MountDao;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Roolli
 */
@RequestScoped
public class MountDaoImpl implements MountDao {

    @PersistenceContext(name = "MountPU")
    private EntityManager em;

    @Override
    public Mount modify(long id, Mount edited) {
        Mount m = em.find(Mount.class, id);
        m.setCarryingCapacity(edited.getCarryingCapacity());
        m.setMaxSpeed(edited.getMaxSpeed());
        m.setMountType(edited.getMountType());
        em.merge(m);
        return m;
    }

    @Override
    public Mount getById(long id) {
        return em.find(Mount.class, id);
    }

    @Override
    public Mount getByName(String name) {
        return (Mount) em.createNamedQuery("Mount.name").setParameter("name", name).getSingleResult();
    }

    @Override
    public boolean isNameAvailable(String name) {
        return em.createNamedQuery("Mount.name").setParameter("name", name).getResultList().isEmpty();
    }

    @Override
    public List<Mount> getAll() {
        return em.createQuery("Select m from Mount m").getResultList();
    }

    @Override
    public long getItemCount() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(Mount.class)));
        return em.createQuery(criteriaQuery).getSingleResult();

    }

    @Override
    public Mount create(Mount newData) {
        em.persist(newData);
        return newData;
    }

    @Override
    public void delete(long id) {

        Mount m = em.find(Mount.class, id);
        em.remove(m);
    }

    @Override
    public List<Mount> get(int start, int count, Mount searchParam, String sortField, String sortDir) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Mount> query = builder.createQuery(Mount.class);
        Root root = query.from(Mount.class);
        query.select(root);
        List<Predicate> predicates = getPredicates(searchParam, builder, root);
        query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        if (sortField != null && sortDir != null) {
            if ("desc".equals(sortDir)) {
                query.orderBy(builder.desc(root.get(sortField)));
            } else if ("asc".equals(sortDir)) {
                query.orderBy(builder.asc(root.get(sortField)));
            }
        }

        return em.createQuery(query).setFirstResult(start).setMaxResults(start + count).getResultList();
    }

    private List<Predicate> getPredicates(Mount predicateSearch, CriteriaBuilder builder, Root root) {
        List<Predicate> preds = new ArrayList<>();
        if (predicateSearch != null && !predicateSearch.getName().isEmpty()) {
            preds.add(builder.like(builder.upper(root.get("name")), "%".concat(predicateSearch.getName().toUpperCase()).concat("%")));
        }
        return preds;
    }

}
