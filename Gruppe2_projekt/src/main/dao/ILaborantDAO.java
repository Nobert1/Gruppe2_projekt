package dao;

import dto.*;
import exception.DALException;

public interface ILaborantDAO extends IUserDAO {

    public void prepareProductBatch(IProductBatchDTO productBatchDTO) throws DALException;
    public void finishBatch(IProductBatchDTO productBatchDTO) throws DALException;
}
