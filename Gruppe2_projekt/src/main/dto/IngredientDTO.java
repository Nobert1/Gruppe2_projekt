package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientDTO implements Serializable, IIngredientDTO {

    private String CommodityName;
    private double amount;
    private int IngredientListID;
    private int versionsnummer;
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

    public void setVersionsnummer(int versionsnummer) {
        this.versionsnummer = versionsnummer;
    }

    public int getVersionsnummer() {
        return versionsnummer;
    }

    @Override
    public int getIngredientListID() {
        return IngredientListID;
    }

    @Override
    public void setIngredientListID(int ingredientlistID) {
        this.IngredientListID = ingredientlistID;
    }

    public IIngredientDTO makeIngredientListFromResultset(ResultSet resultSet) throws SQLException {

        IIngredientDTO IngredientListDTO = new IngredientDTO();

        IngredientListDTO.setCommodityName(resultSet.getString("Råvare_navn"));
        IngredientListDTO.setAmount(resultSet.getDouble("Mængde"));
        IngredientListDTO.setIngredientListID(resultSet.getInt("IngListeID"));
        IngredientListDTO.setVersionsnummer(resultSet.getInt("versionsnummer"));

        return IngredientListDTO;
    }
}