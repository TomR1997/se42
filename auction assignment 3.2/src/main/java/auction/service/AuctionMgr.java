package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class AuctionMgr  {

    private EntityManager entityManager;
    
    public AuctionMgr(EntityManager entityManager){
        this.entityManager =entityManager;
    }
   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        ItemDAO itemDAO = new ItemDAOJPAImpl(entityManager);
        try {
            Item item = itemDAO.find(id);
            if (item != null) {
                return item;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        ItemDAO itemDAO = new ItemDAOJPAImpl(entityManager);
        return itemDAO.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {
        ItemDAO itemDAO = new ItemDAOJPAImpl(entityManager);
        if (item.getHighestBid() == null || item.getHighestBid().getAmount().compareTo(amount) < 0)
        {
            Bid newBid = item.newBid(buyer, amount);
            itemDAO.edit(item);
            return newBid;
        }
        
        return null;
    }
}
