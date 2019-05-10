package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientListDTO implements Serializable, IIngredientListDTO {

    private String CommodityName;
    private double amount;
    private int IngredientListID;
    private int versionNo;

    @Override
    public String getCommodityName() {
        return CommodityName;
    }

    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int getIngredientlistID() {
        return IngredientListID;
    }

    public void setIngredientlistID(int ingredientlistID) {
        this.IngredientListID = ingredientlistID;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public IIngredientListDTO makeIngredientListFromResultset(ResultSet resultSet) throws SQLException {

        IIngredientListDTO IngredientListDTO = new IngredientListDTO();

        IIngredientListDTO.setCommodityName(resultSet.getString("Råvarenavn"));
        IIngredientListDTO.setAmount(resultSet.getDouble("Mængde"));
        IIngredientListDTO.setIngredientListID(resultSet.getInt("Ingrediensliste ID"));
        IIngredientListDTO.setVersionNo(resultSet.getInt("Versionsnummer"));

        return IngredientListDTO;
    }
}