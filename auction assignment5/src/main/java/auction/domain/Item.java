package auction.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import nl.fontys.util.Money;

@Entity (name="ITEM_1")
@NamedQueries({
    @NamedQuery(name = "Item.findById", query = "select i from ITEM_1 i where i.id = :id"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from ITEM_1 i where i.description = :description"),
    @NamedQuery(name = "Item.count", query = "select count(i) from ITEM_1 i"),
    @NamedQuery(name = "Item.getAllItems", query = "select i from ITEM_1 as i")
})
public class Item implements Comparable {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
    private User seller;

    @Embedded
    private Category category;

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHighest(Bid highest) {
        this.highest = highest;
    }
    private String description;

    @OneToOne
    private Bid highest;

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Item() {
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
        if (o == null) {
            return false;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item2 = (Item) o;
        return Objects.equals(this.id, item2.getId()) && Objects.equals(this.getDescription(), item2.getDescription());
    }

    public int hashCode() {
        //TODO
        return 0;
    }
}
