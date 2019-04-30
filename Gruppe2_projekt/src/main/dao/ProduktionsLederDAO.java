package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProduktionsLederDAO extends UserDAO implements IProduktionsLederDAO {

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }


    @Override
    public void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = createConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Råvarebatch VALUES (default , ?, ?, ?)");
            statement.setString(1, commodityBatchDTO.getProducerName());
            statement.setInt(2, commodityBatchDTO.getingredientlistID());
            statement.setDouble(3, commodityBatchDTO.getMængde());

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        }

    @Override
    public void DeleteCommodityBatch(int batchID) throws DALException {
        try (Connection c = createConnection()) {
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
    public void CreateProductBatch() {

    }

    @Override
    public void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = createConnection()) {
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
        try (Connection c = createConnection()) {
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
    public List<ICommodityBatchDTO> StartProductBatch()  {
        List<ICommodityBatchDTO> CommoditybatchList = new ArrayList<>();

        try (Connection c = createConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("SELECT * FROM INGREDIENSLISTE WHERE IngListeID = (?)");

            Scanner scan = new Scanner(System.in);
            System.out.println("What product batch would you like to produce today? Please type in the Ingridentlist ID");
            int IngrediensListeID = scan.nextInt();
            statement.setInt(1, IngrediensListeID);


            String outprint = "take the commodity batches with the numbers ";
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
                outprint += resultSet1.getInt("BatchID") + "\n";
            }
                System.out.println(outprint);

            PreparedStatement statement2 = c.prepareStatement("SELECT Batch_navn FROM Opskrifter WHERE opskriftID = (?)");
            statement2.setInt(1, IngrediensListeID);
            ResultSet resultSet2 = statement2.executeQuery();



            //Det her skal vel være en join af en art...... skal vidst også oprette det objekt der bliver sat ind.

            String batch_name = resultSet2.getString("Batch_navn");

            PreparedStatement statement3 = c.prepareStatement("SELECT * FROM Produktbatch_beskrivelse WHERE Produktbatch_beskrivelse.type = (?)");
            statement3.setString(1, batch_name);

            PreparedStatement statement4 = c.prepareStatement("INSERT INTO Produktbatch VALUES (default, ?, null, ?)");
            statement4.setString(1, batch_name);

                if (resultset.next()) {
                    LocalDate localDate = LocalDate.now();
                    LocalDate localDate1 = localDate.plusDays(resultset.getInt("Opbevarings_dage"));
                    statement4.setDate(2, Date.valueOf(localDate1));
                }
                statement4.setString(2, "Awaiting production");
                int rows = statement4.executeUpdate();

                c.commit();
                /**
                 * Der skal også fjernes fra lageret, forbindelsen til lageret oprettes igennem opskrifter -> ingredienslister -> råvarelager
                 */
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
