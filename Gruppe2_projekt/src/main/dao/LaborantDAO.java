package dao;

import com.mysql.cj.protocol.Resultset;
import dto.*;
import dao.*;
import Exception.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.*;

import java.sql.*;

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
    public void removefromstorage() {
        try (Connection c = createConnection()) {
            PreparedStatement statement = c.prepareStatement("SELECT * FROM INGREDIENSLISTE WHERE IngListeID = (?)");
            ResultSet resultset = statement.executeQuery();
            Scanner scan = new Scanner(System.in);
            System.out.println("What batch would you like to produce today?");
            statement.setInt(1, scan.nextInt());
            c.setAutoCommit(false);
            String outprint = "take from the batches with the numbers ";

            PreparedStatement statement1 = c.prepareStatement
                    ("SELECT * FROM Råvare_batch_lager WHERE Råvare_navn = " + resultset.getString("Råvare_navn") +
                            " AND MIN(Mængde) FROM Råvare_batch_lager WHERE Mængde > " + resultset.getInt("Mængde"));

            PreparedStatement statement2 = c.prepareStatement("UPDATE Råvare_batch_lager WHERE ID = (?) SET Mængde (?)");

            while (resultset.next()) {
            ResultSet resultset1 = statement1.executeQuery();
            if (resultset1.next()) {
            outprint += resultset1.getInt("BatchID") + "\n";
            statement2.setInt(1, resultset1.getInt("BatchID"));
            statement2.setInt(2, resultset1.getInt("Mængde") - resultset.getInt("Mængde"));
            statement2.addBatch();
            }
            }
            int [] rows = statement2.executeBatch();
            System.out.println(outprint);
            c.commit();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}