package dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ICommodityBatchDTO {

    int getBatchID();

    String getProducerName();

    int getRecipeID();

    void setBatchID(int batchID);

    void setProducerName(String producerName);

    void setRecipeID(int recipeID);

    void setActualAmount(Double amount);

    Double getActualAmount();

    void setOriginalAmount (Double amount);

    Double getOriginalAmount();

    ICommodityBatchDTO makeCommodityBatchFromResultset(ResultSet resultSet) throws SQLException;

    String getCommodityName();

    void setCommodityName(String commodityName);

    boolean isRemainder();

    void setRemainder(boolean remainder);

    }
