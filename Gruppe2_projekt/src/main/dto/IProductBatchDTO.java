package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public interface IProductBatchDTO {


    public Date getExpiringdate();

    public int getBatchID();

    public String getBatch_navn();

    public String getStatus();

    public void setBatch_navn(String batch_navn);

    public void setBatchID(int batchID);

    public void setExpiringdate(Date expiringdate);

    public void setStatus(String status);

    public IProductBatchDTO makeproductBatchFromResultset(ResultSet resultSet) throws SQLException;

    }
