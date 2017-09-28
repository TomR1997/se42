package auction.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity (name = "USER_1")
public class User {

    @Id
    @Column(name = "EMAIL")
    private String email;
    public User(String email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    public User(){
        
    }
}
