package dao;
import dto.*;
import dao.*;
import Exception.*;

import java.util.List;


public interface IProduktionsLederDAO extends IUserDAO {
    public void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public void ReadCommodity() throws DALException;
    public void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;

    public void DeleteCommodityBatch(int batchID) throws DALException;

    public void CreateProductBatch();
    public List<ICommodityBatchDTO> StartProductBatch();


        public void StartCommodityBatch();
    //To forskellige ting?
    public void getStorageCommodityStatus();
    public void getStorageCommoditybatchStatus();
}
