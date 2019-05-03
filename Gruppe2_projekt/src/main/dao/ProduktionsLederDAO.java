package dao;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import dto.*;
import dao.*;
import Exception.*;

import javax.sql.*;
import javax.transaction.xa.XAResource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProduktionsLederDAO extends UserDAO implements IProduktionsLederDAO {

        @Override
    public void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Råvarebatch VALUES (? , ?, ?, ?)");
            statement.setInt(1, commodityBatchDTO.getBatchID());
            statement.setString(2, commodityBatchDTO.getProducerName());
            statement.setInt(3, commodityBatchDTO.getingredientlistID());
            statement.setDouble(4, commodityBatchDTO.getMængde());

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        }

    @Override
    public void DeleteCommodityBatch(int batchID) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("DELETE FROM Råvarebatch WHERE batchID = ?");
            statement.setInt(1, batchID);
            int rows = statement.executeUpdate();
            c.commit();


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void CreateProductBatch(IProductBatchDTO productBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Råvarebatch VALUES (default , ?, ?, ?)");
            statement.setString(1, productBatchDTO.getProduktnavn());
            statement.setDate(2, productBatchDTO.getExpiringdate());
            statement.setString(3, productBatchDTO.getStatus());


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement1 = c.prepareStatement("UPDATE Produktbatch WHERE ID = (?) SET Producentnavn = (?), IngListeID = (?), Mængde = (?)");
            statement1.setInt(1, commodityBatchDTO.getBatchID());
            statement1.setString(2, commodityBatchDTO.getProducerName());
            statement1.setInt(3, commodityBatchDTO.getingredientlistID());
            statement1.setDouble(4, commodityBatchDTO.getMængde());

            int row = statement1.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void ReadCommodity() throws DALException {
        try (Connection c = DataSource.getConnection()) {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM Produktbatch where BatchID = (?)");
            ResultSet resultSet = statement.executeQuery();
            IProductBatchDTO productBatchDTO = new ProductBatchDTO();
            productBatchDTO.makeproductBatchFromResultset(resultSet);
            System.out.println(productBatchDTO.toString());


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    @Override
    public List<ICommodityBatchDTO> StartProductBatch(IProductBatchDTO productBatchDTO)  {
        List<ICommodityBatchDTO> CommoditybatchList = new ArrayList<>();

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("SELECT Råvare_navn, Mængde FROM Ingrediensliste " +
                    "JOIN Opskrifter ON Opskrifter.opskriftID = Ingrediensliste.IngListeID WHERE Opskrifter.Produktnavn = (?)");

            /**
             * Kan det her ikke være et join i stedet måske? - Gustav
             * */

            statement.setString(1, productBatchDTO.getProduktnavn());


            //Statement virker på workbench, test om det også virker her
            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM Råvare_batch_lager WHERE Råvare_navn = (?) HAVING MIN(Mængde) > (?)");

            ResultSet resultset = statement.executeQuery();
            while (resultset.next()) {
                statement1.setString(1, resultset.getString("Råvare_navn"));
                statement1.setDouble(2, resultset.getDouble("Mængde"));
                ResultSet resultSet1 = statement1.executeQuery();
                ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();
                commodityBatchDTO.setBatchID(resultSet1.getInt("BatchID"));
                commodityBatchDTO.setMængde(resultSet1.getDouble("Mængde"));
                CommoditybatchList.add(commodityBatchDTO);
            }
            
            //Det her skal vel være en join af en art...... skal vidst også oprette det objekt der bliver sat ind.

            PreparedStatement statement3 = c.prepareStatement("INSERT INTO Produktbatch VALUES (?, ?, null, ?)");
            statement3.setInt(1, productBatchDTO.getBatchID());
            statement3.setString(2, productBatchDTO.getProduktnavn());
            //Den sidste værdi sættes til null da det er udløbsdato. Udløbsdato er først fastlagt når produktet er klar.
            statement3.setString(3, "Awaiting production");

                int rows = statement3.executeUpdate();

                c.commit();

            } catch (SQLException e) {
                e.getMessage();
            }

        return CommoditybatchList;
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
