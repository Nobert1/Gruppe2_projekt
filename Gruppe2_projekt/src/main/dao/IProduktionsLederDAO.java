package dao;
import dto.*;
import dao.*;
import Exception.*;

import java.util.List;


public interface IProduktionsLederDAO extends IUserDAO {
    /**
     * CRUD metoderne commodity
     * @param commodityBatchDTO
     * @throws DALException
     */
    public void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public ICommodityBatchDTO getCommodity(int BatchID) throws DALException;
    public void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public void DeleteCommodityBatch(int batchID) throws DALException;
    public List<ICommodityBatchDTO> getCommodityBatchList() throws DALException;
    public List<ICommodityBatchDTO> getCommodityBatches(IProductBatchDTO productBatchDTO);


        /**
         * Ikke CRUD, stadig commodity
         */
    public void getCommodityBatches();
    public int SumCommodityBatches(String commodityname) throws DALException;

    /**
     * Product Batch methods
     */
    public List<ICommodityBatchDTO> CreateProductBatch(IProductBatchDTO productBatchDTO);
    public ICommodityBatchDTO getProductBatch(int BatchID) throws DALException;
    public void updateProductBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public void DeleteProductBatch(int batchID) throws DALException;
    public void readallProductBatches();
    public List<IProductBatchDTO> getProductBatchList() throws DALException;




        //To forskellige ting?
}
