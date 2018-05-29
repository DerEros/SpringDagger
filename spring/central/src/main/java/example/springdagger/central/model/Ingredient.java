package example.springdagger.central.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    private Long id;
    private Type ingredientType;
    private String name;
    private Float price;

    public Ingredient(Long id, Type ingredientType, String name, Float price) {
        this.id = id;
        this.ingredientType = ingredientType;
        this.name = name;
        this.price = price;
    }

    public Ingredient() {
    }

    public Long getId() {
        return id;
    }

    public Type getIngredientType() {
        return ingredientType;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public enum Type {
        DOUGH,
        SAUCE,
        TOPPING
    }
}
