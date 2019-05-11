package dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IIngredientListDTO {

    double getAmount();

    void setAmount(Double amount);

    int getIngredientListID();

    void setIngredientListID(int ID);

    String getCommodityName();

    void setCommodityName(String name);

    IIngredientListDTO makeIngredientListFromResultset(ResultSet resultSet) throws SQLException;

}
