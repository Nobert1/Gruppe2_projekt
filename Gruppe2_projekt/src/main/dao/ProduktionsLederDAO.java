package dao;

import com.sun.jndi.ldap.pool.PooledConnectionFactory;
import dto.*;
import exception.*;

import javax.sql.*;
import javax.transaction.xa.XAResource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProduktionsLederDAO extends UserDAO implements IProduktionsLederDAO {



    //TODO burde product batches status værdi laves som en enum så den ikke kan få stavefejl, og alt muligt andet?

    /**
     * Commodity batch methods - s185031 Gustav Emil Nobert
     * @param commodityBatchDTO
     * @throws DALException
     */
        @Override
    public void createCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()){

            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Råvare_batch_lager VALUES (? , ?, ?, ?, ?)");

            statement.setInt(1, commodityBatchDTO.getBatchID());
            statement.setString(2, commodityBatchDTO.getProducerName());
            statement.setDouble(3, commodityBatchDTO.getActualAmount());
            statement.setString(4, commodityBatchDTO.getCommodityName());
            statement.setBoolean(5, commodityBatchDTO.isRemainder());

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        }

    @Override
    public ICommodityBatchDTO getCommodity(int BatchID, String producername) throws DALException {
        try (Connection c = DataSource.getConnection()) {

            PreparedStatement statement = c.prepareStatement("SELECT * FROM Råvare_batch_lager where BatchID = (?) AND producentnavn = (?)");
            statement.setInt(1, BatchID);
            statement.setString(2, producername);
            ResultSet resultSet = statement.executeQuery();
            ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();
            if (resultSet.next()) {
            commodityBatchDTO = commodityBatchDTO.makeCommodityBatchFromResultset(resultSet);
            }

            return commodityBatchDTO;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void DeleteCommodityBatch(int batchID, String producername) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("DELETE FROM Råvare_batch_lager WHERE BatchID = (?) AND producentnavn = (?)");
            statement.setInt(1, batchID);
            statement.setString(2, producername);
            int rows = statement.executeUpdate();
            c.commit();


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void UpdateCommodityBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement1 = c.prepareStatement("UPDATE Råvare_batch_lager SET Mængde = (?), Råvare_navn = (?), rest = (?) WHERE BatchID = (?) AND Producentnavn = (?)");

            statement1.setDouble(1, commodityBatchDTO.getActualAmount());
            statement1.setString(2, commodityBatchDTO.getCommodityName());
            statement1.setBoolean(3, commodityBatchDTO.isRemainder());
            statement1.setInt(4, commodityBatchDTO.getBatchID());
            statement1.setString(5, commodityBatchDTO.getProducerName());

            int row = statement1.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public double SumCommodityBatches(String commodityname) throws DALException{
        double sum = -1;
        try (Connection c = DataSource.getConnection()) {

            //TODO - det her kan godt blivee rigtigt sofisikeret, vil vi returnere en liste eller vil vi hellere der bare bliver søgt
            //På et råvarenavn?

            PreparedStatement statement = c.prepareStatement("SELECT SUM(Mængde) AS \"total mængde\" FROM Råvare_batch_lager WHERE Råvare_navn = ? AND rest = 0");

            //1 tallet er booleans, tror 1 betyder falsk.

            statement.setString(1, commodityname);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                sum = resultSet.getInt("total mængde");
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return sum;
        }

    @Override
    public List<ICommodityBatchDTO> getCommodityBatchList() throws DALException {

        // Skal den her metode beskyttes? den får jo bare en liste så det vil et nej?
        try (Connection c = DataSource.getConnection()) {
            List<ICommodityBatchDTO> commodityBatchDTOList = new ArrayList<>();
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Råvare_batch_lager");

            while (resultSet.next()){
                ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();
                commodityBatchDTO.makeCommodityBatchFromResultset(resultSet);
                commodityBatchDTOList.add(commodityBatchDTO);
            }
            return commodityBatchDTOList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /**
     * Product batch methods. - Gustav Emil Nobert
     * @param productBatchDTO
     * @return
     *
     * PRODUCT BATCHES STARTS NOW
     */

    //TODO mangler den her ikke en metode eller to? F.eks noget med nogle opskrifter getRecipe etc.

    @Override
    public void CreateProductBatch(IProductBatchDTO productBatchDTO)  throws DALException{

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            //Det her skal vel være en join af en art...... skal vidst også oprette det objekt der bliver sat ind.

            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Produktbatch VALUES (?, null, ?, ?, ?)");
            statement2.setInt(1, productBatchDTO.getProductBatchID());

            //Den sidste værdi sættes til null da det er udløbsdato. Udløbsdato er først fastlagt når produktet er klar.
            statement2.setInt(2, productBatchDTO.getRecipeID());
            statement2.setInt(3, productBatchDTO.getRecipeID());
            statement2.setString(4, productBatchDTO.getStatus());


            int rows = statement2.executeUpdate();

                c.commit();

            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }

    }


    @Override
    public IProductBatchDTO getProductBatch(int BatchID) throws DALException {
        try (Connection c = DataSource.getConnection()) {

            PreparedStatement statement = c.prepareStatement("SELECT * FROM Produktbatch WHERE ProduktbatchID = (?)");
            statement.setInt(1, BatchID);
            ResultSet resultSet = statement.executeQuery();
            IProductBatchDTO productBatchDTO = new ProductBatchDTO();
            if (resultSet.next()) {
            productBatchDTO = productBatchDTO.makeproductBatchFromResultset(resultSet);
            }

            return productBatchDTO;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void updateProductBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement1 = c.prepareStatement("UPDATE Produktbatch SET Producentnavn = (?), IngListeID = (?), Mængde = (?) WHERE ID = (?)");
            statement1.setString(1, commodityBatchDTO.getProducerName());
            statement1.setInt(2, commodityBatchDTO.getBatchID()); //TODO Bør være ingredients list ID
            statement1.setDouble(3, commodityBatchDTO.getActualAmount()); //TODO Var mængde??
            statement1.setInt(4, commodityBatchDTO.getBatchID());

            int row = statement1.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    //Der skal også slette i råvarebatch liste. On delete cascade måske?
    @Override
    public void DeleteProductBatch(int batchID) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("DELETE FROM Produktbatch WHERE ProduktbatchID = ?");
            statement.setInt(1, batchID);
            int rows = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<IProductBatchDTO> getProductBatchList() throws DALException {

        // Skal den her metode beskyttes? den får jo bare en liste så det vil et nej?

        try (Connection c = DataSource.getConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Produktbatch");
            List<IProductBatchDTO> productBatchDTOList = new ArrayList<>();
            while (resultSet.next()){
                IProductBatchDTO productBatchDTO = new ProductBatchDTO();
                productBatchDTO.makeproductBatchFromResultset(resultSet);
                productBatchDTOList.add(productBatchDTO);
            }
            return productBatchDTOList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public List<Integer> RestList() throws DALException {

        List<Integer> Restlist = new ArrayList<>();

        try (Connection c = DataSource.getConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Råvare_batch_lager WHERE rest = 1");

            while (resultSet.next()) {
                Restlist.add(resultSet.getInt("BatchID"));
            }

        return Restlist;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

        public List<String> ReorderList() throws DALException {

        List<String> Orderlist = new ArrayList<>();

        try (Connection c = DataSource.getConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Genbestillingsliste WHERE Genbestilles = 1");

            while (resultSet.next()) {
                Orderlist.add(resultSet.getString("Råvare_navn"));
            }

            return Orderlist;

    } catch (SQLException e) {
        throw new DALException(e.getMessage());
    }
    }

    @Override
    public List<ICommodityBatchDTO> getCommodityBatches(IProductBatchDTO productBatchDTO) throws DALException{

        List<ICommodityBatchDTO> CommoditybatchList = new ArrayList<>();

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("SELECT Råvare_navn, Mængde FROM Ingrediensliste i " +
                    "LEFT JOIN Opskrifter o ON " +
                    "o.opskriftID = i.IngListeID AND i.versionsnummer = o.versionsnummer WHERE o.opskriftID = (?) AND o.versionsnummer = ?");

            statement.setInt(1, productBatchDTO.getRecipeID());
            statement.setInt(2, productBatchDTO.getVersionsnummer());
            ResultSet resultset = statement.executeQuery();

            //Statement virker på workbench, test om det også virker her
            PreparedStatement statement1 = c.prepareStatement("SELECT BatchID, producentnavn, Råvare_navn, Mængde, rest FROM Råvare_batch_lager WHERE Råvare_navn = (?) GROUP BY BatchID, producentnavn HAVING MIN(Mængde) > (?);");

            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Råvare_batch_liste VALUES (?, ?, ?)");
            statement2.setInt(1, productBatchDTO.getProductBatchID());

            ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();
            while (resultset.next()) {
                statement1.setString(1, resultset.getString("Råvare_navn"));
                statement1.setDouble(2, resultset.getDouble("Mængde"));
                ResultSet resultSet1 = statement1.executeQuery();
                if (resultSet1.next()) {
                    commodityBatchDTO = commodityBatchDTO.makeCommodityBatchFromResultset(resultSet1);
                    CommoditybatchList.add(commodityBatchDTO);
                }
                //De indsættes i den liste der bruges til at holde styr på det.
                statement2.setInt(2, commodityBatchDTO.getBatchID());
                statement2.setString(3, commodityBatchDTO.getProducerName());
                statement2.addBatch();
            }
                int[] rows = statement2.executeBatch();
                c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return CommoditybatchList;
    }
}
