package dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ICommodityBatchDTO {
    int getBatchID();
    String getProducerName();
    int getingredientlistID();

    void setBatchID(int batchID);

    void setProducerName(String producerName);

    void setIngredientListID(int inglisteID);

    public void setMængde(Double mængde);

    public double getMængde();

    public ICommodityBatchDTO makeCommodityBatchFromResultset(ResultSet resultSet) throws SQLException;

    public String getCommodityName();

    public void setCommodityName(String commodityName);

    public boolean isRemainder();

    public void setRemainder(boolean remainder);

    }
