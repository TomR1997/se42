package auction.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Category {

    @Column (name="DESCRIPTION_CATEGORY")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Category() {
        description = "undefined";
    }

    public Category(String description) {
        this.description = description;
    }
}
