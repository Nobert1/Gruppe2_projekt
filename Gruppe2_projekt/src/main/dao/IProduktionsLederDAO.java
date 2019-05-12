package dao;
import dto.*;
import exception.*;

import java.util.List;


public interface IProduktionsLederDAO extends IUserDAO {
    /**
     * CRUD metoderne commodity
     * @param commodityBatchDTO
     * @throws DALException
     */
    public void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public ICommodityBatchDTO getCommodity(int BatchID, String producername) throws DALException;
    public void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public void DeleteCommodityBatch(int batchID, String producername) throws DALException;
    public List<ICommodityBatchDTO> getCommodityBatchList() throws DALException;
    public List<String> ReorderList() throws DALException;
    public List<Integer> RestList() throws DALException;



        /**
         * Ikke CRUD, stadig commodity
         *
         */
        public List<ICommodityBatchDTO> getCommodityBatches(IProductBatchDTO productBatchDTO) throws DALException;
    public double SumCommodityBatches(String commodityname) throws DALException;

    /**
     * Product Batch methods
     */
    public void CreateProductBatch(IProductBatchDTO productBatchDTO) throws DALException;
    public IProductBatchDTO getProductBatch(int BatchID) throws DALException;
    public void updateProductBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    public void DeleteProductBatch(int batchID) throws DALException;
    public List<IProductBatchDTO> getProductBatchList() throws DALException;




        //To forskellige ting?
}
