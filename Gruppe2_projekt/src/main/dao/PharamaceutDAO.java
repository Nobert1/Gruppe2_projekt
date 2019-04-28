package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PharamaceutDAO extends UserDAO implements IPharamaceutDAO {

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }

    @Override
    public void createRecipe() {

        try (Connection c = createConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter ((default),(?))");

            LocalDate localDate = LocalDate.now();
            statement.setDate(2, Date.valueOf(localDate));

            String input = "";
            Scanner scan = new Scanner(System.in);
            ResultSet resultSet = statement.executeQuery("SELECT MAX(opskriftID) FROM Opskrift");


            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Ingrediensliste ((?),(?),(?))");
            //Da værdien ikke er indsat endnu, skal der lige et + 1 på :)
            if (resultSet.next()) {
                statement2.setInt(3, resultSet.getInt("opskriftID") + 1);
            }
            System.out.println("Skriv 'exit' når du er færdig med at indsætte i denne ingrediensliste");
            while (!input.equals("exit")) {
                System.out.println("Hvad er råvarets navn?");
                input = scan.next();
                statement2.setString(1, input);
                System.out.println("Hvor meget af det skal der bruges?");
                statement2.setDouble(2, scan.nextDouble());
                statement2.addBatch();
            }
            int [] rows = statement2.executeBatch();
            int row = statement.executeUpdate();

            c.commit();

            } catch(SQLException e){
                e.getMessage();
            }


        }

    @Override
    public void editRecipe() {

        /**
         * Ændringer i enhvert system der vil indgå i medicinal branhcen vil blive logget, så man skal ikke kunne direkte
         * ændre i opskrifter uden den gamle bliver gemt.
         */

        try (Connection c = createConnection()) {
            c.setAutoCommit(false);

            Scanner scan = new Scanner(System.in);
            System.out.println("What is the ID of the recipe you wish to make changes to?");
            int opskriftID = scan.nextInt();
            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter ((?), (?))");

            LocalDate localDate = LocalDate.now();
            statement.setInt(1, opskriftID);
            statement.setDate(2, Date.valueOf(localDate));

            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Ingrediensliste ((opskriftID), (?))");



        } catch(SQLException e){
            e.getMessage();
        }
    }

    @Override
    public void deleteRecipe() {
        /**
         * SKal man overhovedet kunne det her?
         */
    }
}
