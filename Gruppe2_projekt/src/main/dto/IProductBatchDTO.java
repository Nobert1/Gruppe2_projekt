package dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IProductBatchDTO {


    java.sql.Date getExpirationDate();

    int getProductBatchID();

    String getProductName();

    List<ICommodityBatchDTO> getCommodityBatchDTOList();

    String getStatus();

    void setCommodityBatchDTOList(List<ICommodityBatchDTO> commodityBatchDTOList);

    void setProductName(String productname);

    void setProductBatchID(int batchID);

    void setExpirationDate(Date expirationDate);

    void setStatus(String status);

    IProductBatchDTO makeproductBatchFromResultset(ResultSet resultSet) throws SQLException;

    }