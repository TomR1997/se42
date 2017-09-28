package auction.domain;

import java.util.Iterator;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "USER_1")
public class User {

    @OneToMany
    private Set<Item> offeredItems;

    public Iterator<Item> getOfferedItems() {
        Iterator<Item> iterator = this.offeredItems.iterator();
        return iterator;
    }

    @Id
    @Column(name = "EMAIL")
    private String email;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public User() {

    }
    
    void addItem(Item item){
        this.offeredItems.add(item);
    }
    
    public int numberOfOfferdItems() {
        return this.offeredItems.size();
    }
}
