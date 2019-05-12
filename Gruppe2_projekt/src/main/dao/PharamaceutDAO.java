package dao;

import dto.*;
import exception.DALException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PharamaceutDAO extends UserDAO implements IPharamaceutDAO {


    //TODO Delte? Igen hvordan undgår vi at den varchar der bliver fyldt med alt muligt andet.
    //TODO primary key for opskrifterne skal være unikke og er derfor ID, versionsnummer. Det ville være fedt hvis versionsnummer kunne auto increment baseret på ID.

    /**
     *
     * Pharmaceuten foretager CRUD operationer på opskrifter.
     * @param
     * @throws DALException
     */

    @Override
    public void insertintoGenbestillingsliste(String commodity) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("INSERT INTO Genbestillingsliste VALUES ((?),(?))");
            statement.setString(1, commodity);
            statement.setBoolean(2, false);

            int row = statement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        }

        @Override
    public void deletefromgenbestillingsliste(String commodity) throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("DELETE FROM Genbestillingsliste WHERE Råvare_navn = (?)");
            statement.setString(1, commodity);

            statement.execute();
            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }


    @Override
    public void createRecipe(IRecipeDTO recipeDTO) throws DALException{

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);


            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter VALUES ((?),(?),(?),(?),(?),(?))");

            statement.setInt(1, recipeDTO.getRecipeID());
            statement.setString(2, recipeDTO.getProductName());
            LocalDate localDate = LocalDate.now();
            statement.setDate(3, Date.valueOf(localDate));
            statement.setInt(4, recipeDTO.getDurability());
            statement.setInt(5, recipeDTO.getVersionnumber());
            statement.setString(6, recipeDTO.getStatus());


            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Ingrediensliste VALUES ((?),(?),(?),(?))");


            statement2.setInt(3, recipeDTO.getRecipeID());
            statement2.setInt(4, recipeDTO.getVersionnumber());

            for (IIngredientDTO ingredient : recipeDTO.getIngredientListDTOList()) {
                statement2.setString(1, ingredient.getCommodityName());
                statement2.setDouble(2, ingredient.getAmount());
                statement2.addBatch();
            }

            int row = statement.executeUpdate();
            int[] rows = statement2.executeBatch();




            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }


    }

    @Override
    public void editRecipe(IRecipeDTO recipeDTO) throws DALException{


        //TODO - ved indsætning af opskrifter burde vi ikke lade recipeDTO holde versionsnummer det burde være en form for auto increment.
        //Det ville være fint hvis det var en værdi der auto inc baseret på en værdi en anden kolonne (ID).


        /**
         * Ændringer i enhvert system der vil indgå i medicinal branhcen vil blive logget, så man skal ikke kunne direkte
         * ændre i opskrifter uden den gamle bliver gemt.
         */

        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);



            if (recipeDTO.getStatus().equals("aktiv")) {
                PreparedStatement statement = c.prepareStatement("UPDATE Opskrifter SET status = \"ikke-aktiv\" WHERE status = \"aktiv\" AND opskriftID = (?)");
                statement.setInt(1, recipeDTO.getRecipeID());
                int row = statement.executeUpdate();
            }

            PreparedStatement statement = c.prepareStatement("INSERT INTO Opskrifter VALUES ((?),(?),(?),(?),(?), (?))");


            statement.setInt(1, recipeDTO.getRecipeID());
            statement.setString(2, recipeDTO.getProductName());
            LocalDate localDate = LocalDate.now();
            statement.setDate(3, Date.valueOf(localDate));
            statement.setInt(4, recipeDTO.getDurability());
            statement.setInt(5, recipeDTO.getVersionnumber());
            statement.setString(6, recipeDTO.getStatus());



            PreparedStatement statement2 = c.prepareStatement("INSERT INTO Ingrediensliste VALUES ((?),(?),(?),(?))");

            statement2.setInt(3, recipeDTO.getRecipeID());
            statement2.setInt(4, recipeDTO.getVersionnumber());

            for (IIngredientDTO ingredient : recipeDTO.getIngredientListDTOList()) {
                statement2.setString(1, ingredient.getCommodityName());
                statement2.setDouble(2, ingredient.getAmount());
                statement2.addBatch();
            }

            int row = statement.executeUpdate();
            int[] rows = statement2.executeBatch();

            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteRecipe(int recipeID, int versionNumber) throws DALException {


        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("DELETE FROM Opskrifter WHERE opskriftID = (?) AND versionsnummer = (?)");

            statement.setInt(1, recipeID);
            statement.setInt(2, versionNumber);

            statement.execute();

            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public IRecipeDTO getRecipe(int recipeID, int versionNumber) throws DALException{

        try (Connection c = DataSource.getConnection()) {

            IRecipeDTO recipeDTO = new RecipeDTO();
            PreparedStatement statement = c.prepareStatement("SELECT Råvare_navn, Mængde, Produktnavn, Ændringsdato, IngListeID, status, opskriftID, Opbevarings_dage, Ingrediensliste.versionsnummer " +
                    "FROM Opskrifter INNER JOIN Ingrediensliste ON Ingrediensliste.IngListeID = Opskrifter.opskriftID " +
                    "AND Ingrediensliste.versionsnummer = Opskrifter.versionsnummer WHERE Opskrifter.opskriftID = ? AND Opskrifter.versionsnummer = ?");
            statement.setInt(1, recipeID);
            statement.setInt(2, versionNumber);
            ResultSet resultSet = statement.executeQuery();
            List<IIngredientDTO> ingredientListDTO = new ArrayList<>();
            RecipeDTO recipeDTO1 = new RecipeDTO();

            IIngredientDTO ingredientDTO = new IngredientDTO();
            while (resultSet.next()) {
                    if (recipeDTO.getRecipeID() == 0) {
                    recipeDTO = recipeDTO1.makeRecipeFromResultset(resultSet);
                    }
                    IIngredientDTO ingredientDTO1 = ingredientDTO.makeIngredientListFromResultset(resultSet);
                    ingredientListDTO.add(ingredientDTO1);
                }
                recipeDTO.setIngredientListDTOList(ingredientListDTO);
                return recipeDTO;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}




















