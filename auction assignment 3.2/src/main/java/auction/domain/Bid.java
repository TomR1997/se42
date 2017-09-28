package auction.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@Entity (name="BID_1")
public class Bid {

    @Embedded
    private FontysTime time;
    @OneToOne
    private User buyer;
    @Embedded
    private Money amount;
    @Id
    private Long id;

    public Bid(){}
    
    public Bid(User buyer, Money amount) {
        //TODO
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
