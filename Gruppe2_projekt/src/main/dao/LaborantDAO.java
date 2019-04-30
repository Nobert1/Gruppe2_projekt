package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.*;

import java.sql.*;

/**
 * Author - Gustav EMil Nobert s185031 and Martin Wassman s185029?
 */

public class LaborantDAO extends UserDAO implements ILaborantDAO {

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }

    @Override
    public void producebatch() {
        try (Connection c = createConnection()) {
            c.setAutoCommit(false);
            Scanner scan = new Scanner(System.in);
            System.out.println("What is the type of thy batch? ");
            String input = scan.nextLine();
            PreparedStatement statement = c.prepareStatement
                    ("SELECT * FROM Produktbatch_beskrivelse WHERE Produktbatch_beskrivelse.type = (?)");
            statement.setString(1, input);
            ResultSet resultset = statement.executeQuery();
            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Produktbatch VALUES (default, ?, ?, ?)");
            statement2.setString(2, input);
            int IngrediensListeID;

            if (resultset.next()) {
                LocalDate localDate = LocalDate.now();
                LocalDate localDate1 = localDate.plusDays(resultset.getInt("Opbevarings_dage"));
                statement2.setDate(3, Date.valueOf(localDate1));
                IngrediensListeID = resultset.getInt("opskriftID");
            }
            PreparedStatement statement1 = c.prepareStatement
                    ("SELECT * FROM Produktbatch_beskrivelse WHERE Produktbatch_beskrivelse.type = (?)");
            int rows = statement2.executeUpdate();

            c.commit();
            /**
             * Der skal også fjernes fra lageret, forbindelsen til lageret oprettes igennem opskrifter -> ingredienslister -> råvarelager
             */
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public void removefromstorage(List<ICommodityBatchDTO> list, int prodbatchID) {
        try (Connection c = createConnection()) {

            Scanner scan = new Scanner(System.in);
            c.setAutoCommit(false);


            PreparedStatement statement1 = c.prepareStatement("UPDATE Råvare_batch_lager WHERE ID = (?) SET Mængde (?)");
            for (ICommodityBatchDTO commodityBatchDTO : list) {

                System.out.println("you need to withdraw " + commodityBatchDTO.getMængde() + " from batch number = " + commodityBatchDTO.getMængde());
                System.out.println("how much did you withdraw?");
                int withdrawen = scan.nextInt();

                while (withdrawen >= commodityBatchDTO.getMængde() * 0.98 && commodityBatchDTO.getMængde() * 1.02 >= withdrawen) {
                    System.out.println("withdrawn amount not within the boundaries, please withdraw agian");
                    System.out.println("How much did you withdraw?");
                    withdrawen = scan.nextInt();
                }

                statement1.setInt(1, commodityBatchDTO.getBatchID());
                statement1.setDouble(2, commodityBatchDTO.getMængde() - withdrawen);
                statement1.addBatch();
            }

            PreparedStatement statement2 = c.prepareStatement("UPDATE Productbatch WHERE ID = (?) SET status = (?)");
            statement2.setInt(1, prodbatchID);
            statement2.setString(2, "under production");
            int row = statement2.executeUpdate();
            int[] rows = statement1.executeBatch();
            c.commit();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void finishBatch(int prodbatchID) {

        try (Connection c = createConnection()) {

            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("UPDATE Productbatch WHERE ID = (?) SET status = (?), Udløbsdato = (?)");

            PreparedStatement statement1 = c.prepareStatement("SELECT * FROM INGREDIENSLISTE WHERE IngListeID = (?)");
            ResultSet resultset1 = statement.executeQuery();

            statement.setInt(1, prodbatchID);
            statement.setString(2, "under production");

            if (resultset1.next()) {
                LocalDate localDate = LocalDate.now();
                LocalDate localDate1 = localDate.plusDays(resultset1.getInt("Opbevarings_dage"));
                statement.setDate(3, Date.valueOf(localDate1));
            }

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            e.getMessage();
        }
    }
}