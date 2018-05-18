package example.springdagger.decentral.model;

public class Ingredient {
    public enum Type {
        DOUGH,
        SAUCE,
        TOPPING
    }

    private final Long id;
    private final Type ingredientType;
    private final String name;
    private final Float price;

    public Ingredient(Long id, Type ingredientType, String name, Float price) {
        this.id = id;
        this.ingredientType = ingredientType;
        this.name = name;
        this.price = price;
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
}
