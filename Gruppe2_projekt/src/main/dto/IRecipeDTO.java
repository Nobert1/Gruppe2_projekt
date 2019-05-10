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
    double getVolume();

    void setStatus(String status);
    void setChangeDate(Date changeDate);
    void setProductName(String productName);
    void setVolume(Double volume);
    void setRecipeID(int recipeID);
    void setVersionnumber(int versionnumber);

    List<IIngredientListDTO> getIngredientListDTOList();
    void setIngredientListDTOList(List<IIngredientListDTO> ingredientListDTOList);
    //TODO: Liste skal implementeres i klassen.

    IRecipeDTO makeRecipeFromResultset(ResultSet resultSet) throws SQLException;

}
