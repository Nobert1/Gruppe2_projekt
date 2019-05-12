package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IRecipeDTO {

    Date getChangeDate();
    String getStatus();
    int getRecipeID();
    int getVersionnumber();
    String getProductName();
    public int getDurability();
    public void setDurability(int durability);


    void setStatus(String status);
    void setChangeDate(Date changeDate);
    void setProductName(String productName);
    void setRecipeID(int recipeID);
    void setVersionnumber(int versionnumber);

    List<IIngredientDTO> getIngredientListDTOList();
    void setIngredientListDTOList(List<IIngredientDTO> ingredientListDTOList);
    //TODO: Liste skal implementeres i klassen.

    IRecipeDTO makeRecipeFromResultset(ResultSet resultSet) throws SQLException;

}
