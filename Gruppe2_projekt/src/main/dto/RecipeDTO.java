package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class RecipeDTO implements Serializable, IRecipeDTO {

    private int recipeID;
    private String ProductName;
    private Date changeDate;
    private int versionnumber;
    private String status;
    private Double volume;
    private List<IIngredientListDTO> ingredientListDTOList;

    public Date getChangeDate() {
        return changeDate;
    }

    public String getStatus() {
        return status;
    }

    public List<IIngredientListDTO> getIngredientListDTOList() { return ingredientListDTOList; }

    public void setIngredientListDTOList(List<IIngredientListDTO> ingredientListDTOList) { this.ingredientListDTOList = ingredientListDTOList; }

    public int getRecipeID() {
        return recipeID;
    }

    public int getVersionnumber() {
        return versionnumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public double getVolume() {
        return volume;
    }

    //Setter functions

    public void setStatus(String status) {
        this.status = status;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setVersionnumber(int versionnumber) {
        this.versionnumber = versionnumber;
    }

    @Override
    public IRecipeDTO makeRecipeFromResultset(ResultSet resultSet) throws SQLException {

        IRecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setStatus (resultSet.getString("Status"));
        recipeDTO.setChangeDate(resultSet.getDate("Ændringsdato")); //TODO: Implementer dato format
        recipeDTO.setProductName(resultSet.getString("Produktnavn"));
        recipeDTO.setVolume(resultSet.getDouble("Volumen"));
        recipeDTO.setRecipeID(resultSet.getInt("opskrift ID"));
        recipeDTO.setVersionnumber(resultSet.getInt("Versionsnummer"));

        //TODO: Implementer setfunktion af CommodityBatchListe.

        return recipeDTO;
    }

}
