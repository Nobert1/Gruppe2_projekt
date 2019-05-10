package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientListDTO implements Serializable, IIngredientListDTO {

    private String CommodityName;
    private double amount;
    private int IngredientListID;

    @Override
    public String getCommodityName() {
        return CommodityName;
    }

    @Override
    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int getIngredientListID() {
        return IngredientListID;
    }

    @Override
    public void setIngredientListID(int ingredientlistID) {
        this.IngredientListID = ingredientlistID;
    }

    public IIngredientListDTO makeIngredientListFromResultset(ResultSet resultSet) throws SQLException {

        IIngredientListDTO IngredientListDTO = new IngredientListDTO();

        IngredientListDTO.setCommodityName(resultSet.getString("Råvarenavn"));
        IngredientListDTO.setAmount(resultSet.getDouble("Mængde"));
        IngredientListDTO.setIngredientListID(resultSet.getInt("Ingrediensliste ID"));

        return IngredientListDTO;
    }
}