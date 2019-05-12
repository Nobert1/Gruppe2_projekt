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
    private List<IIngredientDTO> ingredientListDTOList;
    private int durability;

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public String getStatus() {
        return status;
    }

    public List<IIngredientDTO> getIngredientListDTOList() { return ingredientListDTOList; }

    public void setIngredientListDTOList(List<IIngredientDTO> ingredientListDTOList) { this.ingredientListDTOList = ingredientListDTOList; }

    public int getRecipeID() {
        return recipeID;
    }

    public int getVersionnumber() {
        return versionnumber;
    }

    public String getProductName() {
        return ProductName;
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


    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setVersionnumber(int versionnumber) {
        this.versionnumber = versionnumber;
    }

    @Override
    public IRecipeDTO makeRecipeFromResultset(ResultSet resultSet) throws SQLException {

        IRecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setStatus (resultSet.getString("status"));
        recipeDTO.setChangeDate(resultSet.getDate("Ændringsdato")); //TODO: Implementer dato format
        recipeDTO.setProductName(resultSet.getString("Produktnavn"));
        recipeDTO.setRecipeID(resultSet.getInt("opskriftID"));
        recipeDTO.setVersionnumber(resultSet.getInt("Versionsnummer"));
        recipeDTO.setDurability(resultSet.getInt("Opbevarings_dage"));

        //TODO: Implementer setfunktion af CommodityBatchListe.

        return recipeDTO;
    }

}
