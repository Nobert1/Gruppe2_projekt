package dto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProductBatchDTO implements Serializable, IProductBatchDTO {

    int batchID;
    String batch_navn;
    Date expiringdate;
    String status;

    public Date getExpiringdate() {
        return expiringdate;
    }

    public int getBatchID() {
        return batchID;
    }

    public String getBatch_navn() {
        return batch_navn;
    }

    public String getStatus() {
        return status;
    }

    public void setBatch_navn(String batch_navn) {
        this.batch_navn = batch_navn;
    }

    public void setBatchID(int batchID) {
        this.batchID = batchID;
    }

    public void setExpiringdate(Date expiringdate) {
        this.expiringdate = expiringdate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IProductBatchDTO makeproductBatchFromResultset(ResultSet resultSet) throws SQLException {

    IProductBatchDTO productBatchDTO = new ProductBatchDTO();
            productBatchDTO.setBatchID(resultSet.getInt("BatchID"));
            productBatchDTO.setBatch_navn(resultSet.getString("Batch_navn"));
            productBatchDTO.setExpiringdate(resultSet.getDate("Udløbsdato"));
            productBatchDTO.setStatus(resultSet.getString("status"));

        return productBatchDTO;
    }
}
