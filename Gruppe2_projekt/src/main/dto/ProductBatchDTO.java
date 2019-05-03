package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductBatchDTO implements Serializable, IProductBatchDTO {

    int batchID;
    String Produktnavn;
    java.sql.Date expiringdate;
    String status;


    public int getBatchID() {
        return batchID;
    }

    public String getProduktnavn() {
        return Produktnavn;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public Date getExpiringdate() {
        return expiringdate;
    }

    @Override
    public void setExpiringdate(java.util.Date expiringdate) {

    }

    public void setProduktnavn(String Produktnavn) {
        this.Produktnavn = Produktnavn;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public IProductBatchDTO makeproductBatchFromResultset(ResultSet resultSet) throws SQLException {

    IProductBatchDTO productBatchDTO = new ProductBatchDTO();
            productBatchDTO.setBatchID(resultSet.getInt("BatchID"));
            productBatchDTO.setProduktnavn(resultSet.getString("Batch_navn"));
            productBatchDTO.setExpiringdate(resultSet.getDate("Udløbsdato"));
            productBatchDTO.setStatus(resultSet.getString("status"));

        return productBatchDTO;
    }
}
