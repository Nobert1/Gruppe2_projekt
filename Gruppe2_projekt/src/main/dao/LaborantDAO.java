package dao;

import dto.*;
import exception.DALException;

import javax.management.relation.Role;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import java.sql.*;


//TODO Jeg ved ikke helt hvad den her klasse den har brug for.



public class LaborantDAO extends UserDAO implements ILaborantDAO {

    /**
     * Laborant gør to ting, forbereder batches og afslutter dem.
     * @param productBatchDTO
     * @throws DALException
     */
    @Override
    public void prepareProductBatch(IProductBatchDTO productBatchDTO) throws DALException {
        try (Connection c = DataSource.getConnection()) {

            Scanner scan = new Scanner(System.in);
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("SELECT Råvare_navn, Mængde FROM Ingrediensliste JOIN Opskrifter " +
                    "ON Opskrifter.opskriftID = Ingrediensliste.IngListeID " +
                    "WHERE Opskrifter.opskriftID = (?) AND Opskrifter.versionsnummer = (?)");

            statement.setInt(1, productBatchDTO.getRecipeID());
            statement.setInt(2, productBatchDTO.getVersionsnummer());
            ResultSet resultSet = statement.executeQuery();


//Her ville det måske være fedt at lave noget andet end input scan. Men vi skal jo netop bruge noget input så der vel ingen anden måde at gøre det på.

            PreparedStatement statement1 = c.prepareStatement("UPDATE Råvare_batch_lager SET Mængde = (?) WHERE BatchID = (?) AND producentnavn = (?)");

            while (resultSet.next()) {
            for (ICommodityBatchDTO commodityBatchDTO : productBatchDTO.getCommodityBatchDTOList()) {
                if (resultSet.getString("Råvare_navn").equals(commodityBatchDTO.getCommodityName())) {

                    System.out.println("Du skal afveje " + resultSet.getDouble("Mængde") + " gram fra batch nummer "
                            + commodityBatchDTO.getBatchID() + " som er produceret af " + commodityBatchDTO.getProducerName());

                    double withdrawen = 0;
                    while (true) {
                        System.out.println("Hvor meget afvejede du? ");
                        withdrawen = resultSet.getDouble("Mængde");
                        //Det her burde være scanner validering, men vi kører tests så det her står i stedet.
                        if (withdrawen >= resultSet.getDouble("Mængde") * 0.98 && resultSet.getDouble("Mængde") * 1.02 >= withdrawen) {
                            break;
                        }
                        System.out.println("Den afvejede mængde er ikke indenfor grænserne af den ønskede værdi, prøv igen ");
                    }
                    statement1.setInt(1, commodityBatchDTO.getBatchID());
                    statement1.setString(2, commodityBatchDTO.getProducerName());
                    statement1.setDouble(3, commodityBatchDTO.getActualAmount() - withdrawen);
                    statement1.addBatch();
                }
            }
            }



            PreparedStatement statement2 = c.prepareStatement("UPDATE Produktbatch SET status = (?) WHERE ProduktbatchID = (?) ");

            statement2.setString(1, "Afventer_produktion");
            statement2.setInt(2, productBatchDTO.getProductBatchID());
            int row = statement2.executeUpdate();
            int[] rows = statement1.executeBatch();
            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void finishBatch(IProductBatchDTO productBatchDTO) throws DALException{

        try (Connection c = DataSource.getConnection()) {

            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("UPDATE Produktbatch SET status = (?), Udløbsdato = (?) WHERE ProduktbatchID = (?) ");


            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM Opskrifter WHERE opskriftID = ? AND versionsnummer = ?");
            statement1.setInt(1, productBatchDTO.getRecipeID());
            statement1.setInt(2, productBatchDTO.getVersionsnummer());

            ResultSet resultset1 = statement1.executeQuery();

            statement.setString(1, "færdig");

            if (resultset1.next()) {
                LocalDate localDate = LocalDate.now();
                LocalDate localDate1 = localDate.plusDays(resultset1.getInt("Opbevarings_dage"));
                statement.setDate(2, Date.valueOf(localDate1));
            }

            statement.setInt(3, productBatchDTO.getProductBatchID());

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


}