package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PharamaceutDAO extends UserDAO implements IPharamaceutDAO {


    //TODO Delte? Igen hvordan undgår vi at den varchar der bliver fyldt med alt muligt andet.
    //TODO primary key for opskrifterne skal være unikke og er derfor ID, versionsnummer. Det ville være fedt hvis versionsnummer kunne auto increment baseret på ID.

    @Override
    public void createRecipe(IRecipeDTO recipeDTO) {

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter ((?),(?), (?), (?), (?))");


            statement.setInt(1, recipeDTO.getRecipeID());
            statement.setString(2, recipeDTO.getProductName());
            LocalDate localDate = LocalDate.now();
            statement.setDate(3, Date.valueOf(localDate));
            statement.setInt(4, recipeDTO.getVersionnumber());
            statement.setString(5, recipeDTO.getStatus());

            //TODO Skal vi lade objektet have det her eller nah?

            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Ingrediensliste ((?),(?),(?), (?))");
            //Da værdien ikke er indsat endnu, skal der lige et + 1 på :)

                statement2.setInt(3, recipeDTO.getRecipeID());
                statement2.setInt(4, recipeDTO.getVersionnumber());

            for (IIngredientListDTO ingredient : recipeDTO.getIngredientListDTOList()) {
                statement2.setString(1, ingredient.getCommodity_name());
                statement2.setDouble(2, ingredient.getAmount());
                statement2.addBatch();

            }

            int row = statement.executeUpdate();
            int [] rows = statement2.executeBatch();


            c.commit();

            } catch(SQLException e){
                e.getMessage();
            }


        }

    @Override
    public void editRecipe(IRecipeDTO recipeDTO) {


        //TODO - ved indsætning af opskrifter burde vi ikke lade recipeDTO holde versionsnummer det burde være en form for auto increment.
        //Det ville være fint hvis det var en værdi der auto inc baseret på en værdi en anden kolonne (ID).




        /**
         * Ændringer i enhvert system der vil indgå i medicinal branhcen vil blive logget, så man skal ikke kunne direkte
         * ændre i opskrifter uden den gamle bliver gemt.
         */

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            Scanner scan = new Scanner(System.in);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter ((?), (?), (?), (?), (?))");

            LocalDate localDate = LocalDate.now();
            statement.setInt(1, recipeDTO.getRecipeID());
            statement.setString(2, recipeDTO.getProductName());
            statement.setDate(3, Date.valueOf(localDate));
            statement.setInt(4, recipeDTO.getVersionnumber());
            statement.setString(5, recipeDTO.getStatus());

            PreparedStatement statement1 = c.prepareStatement("INSERT INTO Ingrediensliste ((opskriftID), (?))");

            for (IIngredientListDTO ingredient : recipeDTO.getIngredientListDTOList()) {

                statement1.setString(1, ingredient.getCommodity_name());
                statement1.setDouble(2, ingredient.getAmount());
                statement1.addBatch();
            }


            if (recipeDTO.getStatus().equals("active")) {
                PreparedStatement statement2 = c.prepareStatement("UPDATE Opskrifter SET status = \"ikke-aktiv\" WHERE status = \"aktiv\" AND opskriftID = (?)");
                statement1.setInt(1, recipeDTO.getRecipeID());
                int row = statement1.executeUpdate();
            }


            int row1 = statement.executeUpdate();
            int[] rows = statement1.executeBatch();

            c.commit();

        } catch(SQLException e){
            e.getMessage();
        }
    }

    @Override
    public void deleteRecipe() {
        /**
         * SKal man overhovedet kunne det her?
         * Skal vi bare lave den alligevel?
         */
    }
}
