package dto;

import java.io.Serializable;

public class IngredientListDTO implements Serializable, IIngredientListDTO {

    String Commodity_name;
    double amount;
    int ingredientlistID;

    public double getAmount() {
        return amount;
    }

    public int getIngredientlistID() {
        return ingredientlistID;
    }

    public String getCommodity_name() {
        return Commodity_name;
    }
}
