package auction.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(FontysTime time) {
        this.time = time;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
    @Embedded
    private Money amount;

    public long getId() {
        return id;
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
