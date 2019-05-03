package dao;

import dto.*;
import dao.*;
import Exception.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PharamaceutDAO extends UserDAO implements IPharamaceutDAO {


    @Override
    public void createRecipe(IRecipeDTO recipeDTO, List<IIngredientListDTO> IngredientListDTO) {

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter ((default),(?), (?), (?), (?))");



            statement.setString(2, recipeDTO.getProductName());
            LocalDate localDate = LocalDate.now();
            statement.setDate(3, Date.valueOf(localDate));
            statement.setInt(4, recipeDTO.getVersionnumber());
            statement.setString(5, recipeDTO.getStatus());

            ResultSet resultSet = statement.executeQuery("SELECT MAX(opskriftID) FROM Opskrift");
            //TODO Skal vi lade objektet have det her eller nah?

            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Ingrediensliste ((?),(?),(?))");
            //Da værdien ikke er indsat endnu, skal der lige et + 1 på :)
            if (resultSet.next()) {
                statement2.setInt(3, recipeDTO.getRecipeID());
            }
            for (IIngredientListDTO ingredient : IngredientListDTO) {

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
    public void editRecipe(IRecipeDTO recipeDTO, List<IIngredientListDTO> IngredientListDTO) {


        //TODO - de her to er de samme bare med et andet navn?

        /**
         * Ændringer i enhvert system der vil indgå i medicinal branhcen vil blive logget, så man skal ikke kunne direkte
         * ændre i opskrifter uden den gamle bliver gemt.
         */

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            Scanner scan = new Scanner(System.in);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter ((?), (?))");

            LocalDate localDate = LocalDate.now();
            statement.setInt(1, recipeDTO.getRecipeID());
            statement.setString(2, recipeDTO.getProductName());
            statement.setDate(3, Date.valueOf(localDate));
            statement.setInt(4, recipeDTO.getVersionnumber());
            statement.setString(5, recipeDTO.getStatus());

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
