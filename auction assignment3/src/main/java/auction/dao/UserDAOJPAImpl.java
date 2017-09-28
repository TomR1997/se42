package auction.dao;

import auction.domain.User;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserDAOJPAImpl implements UserDAO {
    
    EntityManagerFactory ef = Persistence.createEntityManagerFactory("userPU");
    EntityManager entityManager = ef.createEntityManager();

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public UserDAOJPAImpl() {
    }

    @Override
    public int count() {
        int count = (Integer) entityManager.createNamedQuery("SELECT count(u) FROM USER_1 u")
                .getSingleResult();
        return count;
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }


    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM USER_1 u").getResultList();
    }

    @Override
    public User findByEmail(String email) {
        return entityManager.find(User.class, email);
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user.getEmail());
    }
}
