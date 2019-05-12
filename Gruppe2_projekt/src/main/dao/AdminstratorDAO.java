package dao;

import dto.IUserDTO;
import exception.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AdminstratorDAO extends UserDAO implements IAdminstratorDAO {

    //TODO as of 05-05-2019 er denne her præcis den samme som den var i anden jdbc aflevering. Spørgsmålet er om den også skal have noget rolle validering?

    @Override
    public void createUser(IUserDTO user) throws DALException {
        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement usersstatement = c.prepareStatement("INSERT INTO Brugere VALUES (?, ?, ?)");
            String roleString = String.join(";", user.getRoles());
            usersstatement.setInt(1, user.getUserId());
            usersstatement.setString(2, user.getUserName());
            usersstatement.setString(3, user.getIni());


            String[] roleArray = roleString.split(";");
            List<String> roleList = Arrays.asList(roleArray);
            PreparedStatement rolesstatement = c.prepareStatement("INSERT INTO Roller VALUES (?, ?)");

            for (int i = 0; i < roleList.size(); i++) {
                rolesstatement.setInt(1, user.getUserId());
                rolesstatement.setString(2, roleList.get(i));
                rolesstatement.addBatch();
            }

            int userrows = usersstatement.executeUpdate();
            int rolesrow = rolesstatement.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public void updateUser(IUserDTO user) throws DALException {

        try (Connection c = DataSource.getConnection()){

            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("UPDATE Brugere SET brugerNavn=(?), Initialer=(?)");

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getIni());
            List<String> roleStrings = user.getRoles();


            PreparedStatement statement2 = c.prepareStatement("UPDATE Roller SET Rolle = (?), BrugerID = (?)" );
            statement2.setInt(2, user.getUserId());
            for (int i = 0; i < roleStrings.size(); i++) {
                statement2.setString(1, roleStrings.get(i));
                statement2.addBatch();
            }
            int[] rows2 = statement2.executeBatch();
            int rows = statement.executeUpdate();

            c.commit();


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("DELETE FROM Brugere WHERE BrugerID = ?");
            statement.setInt(1, userId);
            PreparedStatement statement1 = c.prepareStatement("DELETE FROM Roller WHERE BrugerID = ?");
            statement1.setInt(1,userId);
            int rows = statement.executeUpdate();
            int rows1 = statement1.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

}

