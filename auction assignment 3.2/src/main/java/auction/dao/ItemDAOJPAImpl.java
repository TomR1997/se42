/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.dao;

import auction.domain.Item;
import auction.domain.User;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Tomt
 */
public class ItemDAOJPAImpl implements ItemDAO {

    private EntityManager entityManager;
    private final EntityTransaction tX;

    public ItemDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.tX = entityManager.getTransaction();
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        if (find(item.getId()) != null) {
            throw new EntityExistsException();
        }

        tX.begin();
        try {
            entityManager.persist(item);
            tX.commit();
        } catch (Exception e) {
            tX.rollback();
        }entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }

    @Override
    public void edit(Item item) {
        if (find(item.getId()) == null) {
            throw new IllegalArgumentException();
        }

        tX.begin();
        try {
            entityManager.merge(item);
            tX.commit();
        } catch (Exception e) {
            tX.rollback();
        }
    }

    @Override
    public List<Item> findAll() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Item.class));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public void remove(Item item) {
        tX.begin();
        try {
            entityManager.remove(entityManager.merge(item));
            tX.commit();
        } catch (Exception e) {
            tX.rollback();
        }
    }

    @Override
    public Item find(Long id) {
        Query q = entityManager.createNamedQuery("Item.findByID", Item.class);
        q.setParameter("ID", id);
        try {
            Item item = (Item) q.getSingleResult();
            return item;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = entityManager.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);
        try {
            return (List<Item>) q.getResultList();           
            
        } catch (NoResultException ex) {
            return null;
        }
    }
}
