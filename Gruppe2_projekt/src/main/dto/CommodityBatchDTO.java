package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommodityBatchDTO implements Serializable, ICommodityBatchDTO {

    private int batchID;
    private String producername;
    private int recipeID;
    private Double originalAmount;
    private Double actualAmount;
    private String CommodityName;
    private boolean remainder;

    @Override
    public boolean isRemainder() {
        return remainder;
    }

    @Override
    public void setRemainder(boolean remainder) {
        this.remainder = remainder;
    }

    @Override
    public int getBatchID() {
        return this.batchID;
    }

    @Override
    public String getCommodityName() {
        return CommodityName;
    }

    @Override
    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    @Override
    public String getProducerName() {
        return this.producername;
    }

    @Override
    public int getRecipeID() {
        return this.recipeID;
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
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    @Override
    public Double getOriginalAmount() {        return originalAmount;    }

    @Override
    public void setOriginalAmount(Double originalAmount) {        this.originalAmount = originalAmount;    }

    @Override
    public Double getActualAmount() {        return actualAmount;    }

    @Override
    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public ICommodityBatchDTO makeCommodityBatchFromResultset(ResultSet resultSet) throws SQLException {

        ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();

        commodityBatchDTO.setBatchID(resultSet.getInt("BatchID"));
        commodityBatchDTO.setProducerName(resultSet.getString("producentnavn"));
        commodityBatchDTO.setActualAmount(resultSet.getDouble("Mængde"));
        commodityBatchDTO.setCommodityName(resultSet.getString("Råvare_navn"));
        commodityBatchDTO.setRemainder(resultSet.getBoolean("rest"));

        return commodityBatchDTO;
    }
}
