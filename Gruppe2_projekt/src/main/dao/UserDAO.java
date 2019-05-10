package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import Exception.DALException;
import dto.IUserDTO;
import dto.*;

import javax.management.relation.Role;

/**
 * Class more or less copypasted from s185031 2nd assignment, contains a few changes.
 */

    public class UserDAO implements IUserDAO {

    @Override
    public IUserDTO getUser(int userId) throws DALException {

        String SQL = "SELECT * FROM Brugere JOIN roles ON users.userId = roles.userId WHERE (?) = (?)";
        //TODO Implement this - should retrieve a user from db and parse it to a UserDTO

        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement(SQL);
            statement.setInt(1, userId);
            //Redundant kode.....
            statement.setInt(2, userId);

            ResultSet resultset = statement.executeQuery();
            IUserDTO user = null;
            if (resultset.next()){
                user = makeUserFromResultset(resultset);
            }
            //TODO: Make a user from the resultset (is done?)
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users JOIN roles ON users.userId = roles.userId");
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

    private IUserDTO makeUserFromResultset(ResultSet resultSet) throws SQLException {
        IUserDTO user = new userDTO();
        user.setUserId(resultSet.getInt("userId"));
        user.setUserName(resultSet.getString("userName"));
        user.setIni(resultSet.getString("ini"));

        //Extract roles as String
        String roleString = resultSet.getString("roles");
        //Split string by ;
        String[] roleArray = roleString.split(";");
        //Convert to List
        List<String> roleList = Arrays.asList(roleArray);
        user.setRoles(roleList);
        return user;
    }
}