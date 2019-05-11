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

    void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    ICommodityBatchDTO getCommodity(int BatchID, String username) throws DALException;
    void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    void DeleteCommodityBatch(int batchID, String producername) throws DALException;
    List<ICommodityBatchDTO> getCommodityBatchList() throws DALException;
    List<ICommodityBatchDTO> getCommodityBatches(IProductBatchDTO productBatchDTO);

        /**
         * Ikke CRUD, stadig commodity
         */

    //TODO Implement this?: void getCommodityBatches();
    int SumCommodityBatches(String commodityname) throws DALException;

    /**
     * Product Batch methods
     */
    List<ICommodityBatchDTO> CreateProductBatch(IProductBatchDTO productBatchDTO);
    ICommodityBatchDTO getProductBatch(int BatchID) throws DALException;
    void updateProductBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException;
    void DeleteProductBatch(int batchID) throws DALException;
    void readallProductBatches();
    List<IProductBatchDTO> getProductBatchList() throws DALException;

        //To forskellige ting?
}
