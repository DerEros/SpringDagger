package example.springdagger.decentral.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    public enum Type {
        DOUGH,
        SAUCE,
        TOPPING
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setIngredientType(Type ingredientType) {
        this.ingredientType = ingredientType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
