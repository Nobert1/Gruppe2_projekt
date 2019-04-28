package dao;
import dto.*;
import dao.*;
import Exception.*;


public interface IProduktionsLederDAO extends IUserDAO {
    public void createCommodityBatch();
    public void DeleteCommodityBatch();
    public void UpdateCommodityBatch();
    public void ReadCommodity();

    public void CreateProductBatch();
    public void StartProductBatch();

    public void StartCommodityBatch();
    //To forskellige ting?
    public void getStorageCommodityStatus();
    public void getStorageCommoditybatchStatus();
}
