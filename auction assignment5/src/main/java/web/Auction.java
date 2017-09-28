/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.util.List;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.fontys.util.Money;

/**
 *
 * @author Tomt
 */
@WebService
public class Auction {
    private final AuctionMgr auctionMgr = new AuctionMgr();
    private final SellerMgr sellerMgr = new SellerMgr();
    private final EntityManagerFactory f = Persistence.createEntityManagerFactory("userPU");
    private EntityManager em;
    
    public Auction() {
    }
    
    public Item getItem(Long id){
        return auctionMgr.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description){
        return auctionMgr.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount){
        return auctionMgr.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category cat, String description){
        return sellerMgr.offerItem(seller, cat, description);
    }
    
    public boolean revokeItem(Item item){
        return sellerMgr.revokeItem(item);
    }
}
