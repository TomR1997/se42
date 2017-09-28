/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.dao;

import auction.domain.Item;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Tomt
 */
public class ItemDAOJPAImpl implements ItemDAO {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }
    public ItemDAOJPAImpl() {
        this.entityManager = Persistence.createEntityManagerFactory("userPU").createEntityManager();  
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("Item.count", Item.class);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }

    @Override
    public void edit(Item item) {
        entityManager.merge(item);
    }

    @Override
    public List<Item> findAll() {
         return entityManager.createNamedQuery("Item.getAllItems", Item.class).getResultList();
    }

    @Override
    public void remove(Item item) {
        entityManager.getTransaction().begin();
        entityManager.remove(this.find(item.getId()));
        entityManager.getTransaction().commit();
    }

    @Override
    public Item find(Long id) {
        Query q = entityManager.createNamedQuery("Item.findById", Item.class);
        q.setParameter("id", id);
        Item item;
        try {
            item = (Item) q.getSingleResult();
        } catch (NoResultException e) {
            item=null;
        }
        return item;
    }

    @Override
    public List<Item> findByDescription(String description) {
        Query q = entityManager.createNamedQuery("Item.findByDescription", Item.class);
        return q.setParameter("description", description).getResultList();
    }
}
