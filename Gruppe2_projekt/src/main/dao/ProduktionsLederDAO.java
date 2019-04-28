package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ProduktionsLederDAO extends UserDAO implements IProduktionsLederDAO {

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }

    @Override
    public void createCommodityBatch() {

    }

    @Override
    public void DeleteCommodityBatch() {

    }

    @Override
    public void UpdateCommodityBatch() {

    }

    @Override
    public void ReadCommodity() {

    }

    @Override
    public void CreateProductBatch() {

    }

    @Override
    public void StartProductBatch() {

    }

    @Override
    public void StartCommodityBatch() {

    }

    @Override
    public void getStorageCommodityStatus() {

    }

    @Override
    public void getStorageCommoditybatchStatus() {

    }
}
