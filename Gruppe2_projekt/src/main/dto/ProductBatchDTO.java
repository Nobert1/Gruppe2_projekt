package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductBatchDTO implements Serializable, IProductBatchDTO {

    private int batchID;
    private String productName;
    private java.sql.Date expirationDate;
    private String status;
    private List<ICommodityBatchDTO> commodityBatchDTOList;

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
    public String getProductName() {
        return productName;
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
    public void setProductName(String productName) {
        this.productName = productName;
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
            productBatchDTO.setProductBatchID(resultSet.getInt("BatchID"));
            productBatchDTO.setProductName(resultSet.getString("Batch_navn"));
            productBatchDTO.setExpirationDate(resultSet.getDate("Udløbsdato"));
            productBatchDTO.setStatus(resultSet.getString("status"));

            //TODO: Implementer setfunktion af CommodityBatchListe.

        return productBatchDTO;
    }
}
