package auction.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity (name="BID_1")
public class Bid {

    @Id
    private long id;
    
    @Embedded
    private FontysTime time;
    @OneToOne
    private User buyer;
    @Embedded
    private Money amount;
    
    @OneToOne(mappedBy="highest")
    private Item madeFor;

    public Item getMadeFor() {
        return madeFor;
    }

    public Bid(){}
    public Bid(User buyer, Money amount) {
        this.buyer=buyer;
        this.amount=amount;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
  
}
