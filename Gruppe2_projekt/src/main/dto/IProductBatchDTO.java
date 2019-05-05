package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IProductBatchDTO {


    public java.sql.Date getExpiringdate();

    public int getBatchID();

    public String getProduktnavn();
    public List<ICommodityBatchDTO> getCommodityBatchDTOList();
    public void setCommodityBatchDTOList(List<ICommodityBatchDTO> commodityBatchDTOList);
    public String getStatus();

    public void setProduktnavn(String Produktnavn);

    public void setBatchID(int batchID);

    public void setExpiringdate(Date expiringdate);

    public void setStatus(String status);

    public IProductBatchDTO makeproductBatchFromResultset(ResultSet resultSet) throws SQLException;

    }
