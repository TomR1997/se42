package auction.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import nl.fontys.util.Money;

@Entity(name = "ITEM_1")
@NamedQueries({
    @NamedQuery(name = "Item.getAll", query = "select i from ITEM_1 as i"),
    @NamedQuery(name = "Item.count", query = "select count(i) from ITEM_1 as i"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from ITEM_1 as i where i.description = :description"),
    @NamedQuery(name = "Item.findByID", query = "select i from ITEM_1 as i where i.id = :ID")
})
public class Item implements Comparable {

    @Id
    private Long id;
    @ManyToOne
    private User seller;
    @Embedded

    private Category category;
    private String description;
    @OneToOne
    private Bid highest;

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    public boolean equals(Object o) {
        //TODO
        return false;
    }

    public int hashCode() {
        //TODO
        return 0;
    }
}
