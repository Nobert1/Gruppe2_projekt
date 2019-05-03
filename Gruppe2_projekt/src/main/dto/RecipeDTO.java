package dto;

import java.io.Serializable;
import java.util.Date;

public class RecipeDTO implements Serializable, IRecipeDTO {

    int recipeID;
    String ProductName;
    Date changeDate;
    int versionnumber;
    String status;
    double volume;

    public Date getChangeDate() {
        return changeDate;
    }

    public String getStatus() {
        return status;
    }



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

    public void setStatus(String status) {
        this.status = status;
    }



    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setVersionnumber(int versionnumber) {
        this.versionnumber = versionnumber;
    }
}
