package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductBatchDTO implements Serializable, IProductBatchDTO {

    private int batchID;
    private java.sql.Date expirationDate;
    private String status;
    private List<ICommodityBatchDTO> commodityBatchDTOList;
    private int recipeID;
    private int versionsnummer;



    @Override
    public int getBatchID() {
        return batchID;
    }

    @Override
    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    @Override
    public int getVersionsnummer() {
        return versionsnummer;
    }


    @Override
    public int getRecipeID() {
        return recipeID;
    }

    @Override
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    @Override
    public void setVersionsnummer(int versionsnummer) {
        this.versionsnummer = versionsnummer;
    }

    @Override
    public List<ICommodityBatchDTO> getCommodityBatchDTOList() {
        return commodityBatchDTOList;
    }

    @Override
    public void setCommodityBatchDTOList(List<ICommodityBatchDTO> commodityBatchDTOList) {
        this.commodityBatchDTOList = commodityBatchDTOList;    }

    @Override
    public int getProductBatchID() {
        return batchID;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public void setExpirationDate(java.util.Date expirationDate) {
        //TODO: Implementer expiration date i forhold til SQL format.
    }


    @Override
    public void setProductBatchID(int batchID) {
        this.batchID = batchID;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public IProductBatchDTO makeproductBatchFromResultset(ResultSet resultSet) throws SQLException {

            IProductBatchDTO productBatchDTO = new ProductBatchDTO();
            productBatchDTO.setProductBatchID(resultSet.getInt("ProduktbatchID"));
            productBatchDTO.setExpirationDate(resultSet.getDate("Udløbsdato"));
            productBatchDTO.setStatus(resultSet.getString("status"));
            productBatchDTO.setRecipeID(resultSet.getInt("opskriftID"));
            productBatchDTO.setVersionsnummer(resultSet.getInt("versionsnummer"));

            //TODO: Implementer setfunktion af CommodityBatchListe.

        return productBatchDTO;
    }
}
