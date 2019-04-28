package dao;

import dto.IAdminstratorDTO;
import dto.IUserDTO;
import Exception.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AdminstratorDAO extends UserDAO implements IAdminstratorDAO {

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?"
                + "user=s185031&password=UfudYEA2p7RmipWZXxT2R");
    }

    @Override
    public void createUser(IUserDTO user) throws DALException {
        try (Connection c = createConnection()){
            c.setAutoCommit(false);
            PreparedStatement usersstatement = c.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
            String roleString = String.join(";", user.getRoles());
            usersstatement.setInt(1, user.getUserId());
            usersstatement.setString(2, user.getIni());
            usersstatement.setString(3, user.getUserName());

            String[] roleArray = roleString.split(";");
            List<String> roleList = Arrays.asList(roleArray);
            PreparedStatement rolesstatement = c.prepareStatement("INSERT INTO roles VALUES (?, ?)");

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

        try (Connection c = createConnection()){

            c.setAutoCommit(false);

            PreparedStatement statement = c.prepareStatement("UPDATE users SET userName=(?), ini=(?)");

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getIni());
            List<String> roleStrings = user.getRoles();


            PreparedStatement statement2 = c.prepareStatement("UPDATE roles SET roles = (?), userID = (?)" );
            statement2.setInt(2, user.getUserId());
            for (int i = 0; i < roleStrings.size(); i++) {
                statement2.setString(1, roleStrings.get(i));
                statement2.addBatch();
            }
            int rows2 = statement2.executeUpdate();
            int rows = statement.executeUpdate();

            c.commit();


        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection c = createConnection()){
            c.setAutoCommit(false);
            PreparedStatement statement = c.prepareStatement("DELETE FROM users WHERE userId = ?");
            statement.setInt(1, userId);
            int rows = statement.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

}

