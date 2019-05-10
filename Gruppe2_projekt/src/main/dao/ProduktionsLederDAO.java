package dao;

import dto.*;
import Exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            statement.setDouble(3, commodityBatchDTO.getAmount());
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
            commodityBatchDTO.makeCommodityBatchFromResultset(resultSet);

            return commodityBatchDTO;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void DeleteCommodityBatch(int batchID, String producername) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("DELETE FROM Råvare_batch_lager WHERE BatchID = ? AND producentnavn = (?)");
            statement.setInt(1, batchID);
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

            statement1.setDouble(1, commodityBatchDTO.getAmount());
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
    public int SumCommodityBatches(String commodityname) throws DALException{
        int sum = -1;
        try (Connection c = DataSource.getConnection()) {

            //TODO - det her kan godt blivee rigtigt sofisikeret, vil vi returnere en liste eller vil vi hellere der bare bliver søgt
            //På et råvarenavn?

            PreparedStatement statement = c.prepareStatement("SELECT SUM(Mængde) " +
                    "FROM Råvare_batch_lager " +
                    "WHERE Råvare_navn = (?) AND rest = 1;");

            //1 tallet er booleans, tror 1 betyder falsk.

            statement.setString(1, commodityname);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                sum = resultSet.getInt("Mængde");
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
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * PRODUCT BATCHES STARTS NOW
     */



    //TODO mangler den her ikke en metode eller to? F.eks noget med nogle opskrifter getRecipe etc.

    @Override
    public List<ICommodityBatchDTO> CreateProductBatch(IProductBatchDTO productBatchDTO)  {
        List<ICommodityBatchDTO> CommoditybatchList = new ArrayList<>();

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            //Det her skal vel være en join af en art...... skal vidst også oprette det objekt der bliver sat ind.

            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Produktbatch VALUES (?, ?, null, ?)");
            statement2.setInt(1, productBatchDTO.getBatchID());
            statement2.setString(2, productBatchDTO.getProduktnavn());
            //Den sidste værdi sættes til null da det er udløbsdato. Udløbsdato er først fastlagt når produktet er klar.
            statement2.setString(3, "Awaiting production");

                int rows = statement2.executeUpdate();

                c.commit();

            } catch (SQLException e) {
                e.getMessage();
            }

        return CommoditybatchList;
    }


    @Override
    public ICommodityBatchDTO getProductBatch(int BatchID) throws DALException {
        try (Connection c = DataSource.getConnection()) {

            PreparedStatement statement = c.prepareStatement("SELECT * FROM Produktbatch where BatchID = (?)");
            statement.setInt(1, BatchID);
            ResultSet resultSet = statement.executeQuery();
            ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();
            commodityBatchDTO.makeCommodityBatchFromResultset(resultSet);

            return commodityBatchDTO;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void updateProductBatch(ICommodityBatchDTO commodityBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement1 = c.prepareStatement("UPDATE Produktbatch WHERE ID = (?) SET Producentnavn = (?), IngListeID = (?), Mængde = (?)");
            statement1.setInt(1, commodityBatchDTO.getBatchID());
            statement1.setString(2, commodityBatchDTO.getProducerName());
            statement1.setInt(3, commodityBatchDTO.getIngredientlistID());
            statement1.setDouble(4, commodityBatchDTO.getAmount());

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

    @Override
    public List<ICommodityBatchDTO> getCommodityBatches(IProductBatchDTO productBatchDTO) {

        List<ICommodityBatchDTO> CommoditybatchList = new ArrayList<>();

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("SELECT Råvare_navn, Mængde FROM Ingrediensliste JOIN Opskrifter " +
                    "ON Opskrifter.opskriftID = Ingrediensliste.IngListeID" +
                    "WHERE Opskrifter.Produktnavn = (?) AND Opskrifter.status = \"aktiv\"");

            /**
             * Her skal der måske være nogle ændringer, baseret på bare ID får vi så den rigtige?
             * Skal vi i virkeligheden have den tabel til gamle opskrifter? - Gustav
             * */

            statement.setString(1, productBatchDTO.getProduktnavn());
            ResultSet resultset = statement.executeQuery();

            //Statement virker på workbench, test om det også virker her
            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM Råvare_batch_lager WHERE Råvare_navn = (?) HAVING MIN(Mængde) > (?)");
            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Råvare_batch_liste VALUES (?, ?, ?)");

            statement2.setInt(1, productBatchDTO.getBatchID());


            while (resultset.next()) {
                statement1.setString(1, resultset.getString("Råvare_navn"));
                statement1.setDouble(2, resultset.getDouble("Mængde"));
                ResultSet resultSet1 = statement1.executeQuery();
                ICommodityBatchDTO commodityBatchDTO = new CommodityBatchDTO();
                commodityBatchDTO.setBatchID(resultSet1.getInt("BatchID"));
                commodityBatchDTO.setAmount(resultSet1.getDouble("Mængde"));
                CommoditybatchList.add(commodityBatchDTO);

                //De indsættes i den liste der bruges til at holde styr på det.
                statement2.setString(2, resultset.getString("producentnavn"));
                statement2.setInt(3, resultset.getInt("BatchID"));
                statement2.addBatch();
            }
                int[] rows = statement2.executeBatch();
                c.commit();

        } catch (SQLException e) {
            e.getMessage();
        }
        return CommoditybatchList;
    }
}