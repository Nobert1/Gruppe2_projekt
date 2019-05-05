package dto;

import java.util.Date;
import java.util.List;

public interface IRecipeDTO {

    public Date getChangeDate();
    public String getStatus();
    public int getRecipeID();
    public int getVersionnumber();
    public String getProductName();
    public double getVolume();
    public void setStatus(String status);
    public void setChangeDate(Date changeDate);
    public void setProductName(String productName);
    public void setVolume(double volume);
    public void setRecipeID(int recipeID);

    public List<IIngredientListDTO> getIngredientListDTOList();
    public void setIngredientListDTOList(List<IIngredientListDTO> ingredientListDTOList);


    public void setVersionnumber(int versionnumber);
}
