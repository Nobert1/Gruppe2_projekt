package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import exception.DALException;
import dto.IUserDTO;
import dto.*;

/**
 * Class more or less copypasted from s185031 2nd assignment, contains a few changes.
 */
public class UserDAO implements IUserDAO {

    @Override
    public IUserDTO getUser(int userId) throws DALException {

        String SQL = "SELECT * FROM Brugere  WHERE BrugerID = (?)";
        //TODO Implement this - should retrieve a user from db and parse it to a UserDTO


        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(SQL);
            statement.setInt(1, userId);

            ResultSet resultset = statement.executeQuery();
            IUserDTO user = null;
            if (resultset.next()){
                user = makeUserFromResultset(resultset);
            }
            c.commit();
            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    @Override
    public List<IUserDTO> getUserList() throws DALException {


        // Skal den her metode beskyttes? den får jo bare en liste så det vil et nej?

        try (Connection c = DataSource.getConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Brugere JOIN Roller ON Brugere.BrugerID = Roller.BrugerID");
            List<IUserDTO> userList = new ArrayList<>();
            while (resultSet.next()){
                IUserDTO user = makeUserFromResultset(resultSet);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    private IUserDTO makeUserFromResultset(ResultSet resultSet)throws DALException {
        try (Connection c = DataSource.getConnection()) {
            IUserDTO user = new UserDTO();
            user.setUserId(resultSet.getInt("BrugerID"));
            user.setUserName(resultSet.getString("brugerNavn"));
            user.setIni(resultSet.getString("Initialer"));
            //Extract roles as String

            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("SELECT * FROM Roller WHERE BrugerID = ?");
            statement.setInt(1, resultSet.getInt("BrugerID"));
            ResultSet resultSetRoles = statement.executeQuery();
            List<String> roleList = new ArrayList<>();
            c.commit();
            while (resultSetRoles.next()) {
                roleList.add(resultSetRoles.getString("Rolle"));
            }
            user.setRoles(roleList);
            return user;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
/* String roleString = resultSet.getString("Rolle");
        //Split string by ;
        String[] roleArray = roleString.split(";");
        //Convert to List
        List<String> roleList = Arrays.asList(roleArray);
        user.setRoles(roleList);*/