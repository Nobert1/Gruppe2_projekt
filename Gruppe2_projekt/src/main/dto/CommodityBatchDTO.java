package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CommodityBatchDTO implements Serializable, ICommodityBatchDTO {

    private int batchID;
    private String producername;
    private int InglisteID;
    private Double Mængde;

    @Override
    public int getBatchID() {
        return this.batchID;
    }

    @Override
    public String getProducerName() {
        return this.producername;
    }

    @Override
    public int getingredientlistID() {
        return this.InglisteID;
    }

    @Override
    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    @Override
    public void setProducerName(String producerName) {
        this.producername = producerName;
    }

    @Override
    public void setIngredientListID(int inglisteID) {
        this.InglisteID = inglisteID;
    }

    @Override
    public void setMængde(Double mængde) {
        Mængde = mængde;
    }


    public double getMængde() {
        return Mængde;
    }

    public ICommodityBatchDTO makeCommodityBatchFromResultset(ResultSet resultSet) throws SQLException {
        ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();


        commodityBatchDTO.setBatchID(resultSet.getInt("BatchID"));
        commodityBatchDTO.setProducerName(resultSet.getString("Producentnavn"));
        commodityBatchDTO.setIngredientListID(resultSet.getInt("IngListeID"));
        commodityBatchDTO.setMængde(resultSet.getDouble("Mængde"));

        return commodityBatchDTO;
    }
}
