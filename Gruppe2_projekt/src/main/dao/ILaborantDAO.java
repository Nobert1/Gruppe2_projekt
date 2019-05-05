package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.util.List;

public interface ILaborantDAO extends IUserDAO {

    void prepareProductBatch(IProductBatchDTO productBatchDTO);

    void finishBatch(IProductBatchDTO productBatchDTO);
}
