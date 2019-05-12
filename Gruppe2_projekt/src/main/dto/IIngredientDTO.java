package dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IIngredientDTO {

    double getAmount();

    void setAmount(Double amount);

    int getIngredientListID();

    void setIngredientListID(int ID);

    String getCommodityName();

    void setCommodityName(String name);

    IIngredientDTO makeIngredientListFromResultset(ResultSet resultSet) throws SQLException;

    public void setVersionsnummer(int versionsnummer);
    public int getVersionsnummer();
}
