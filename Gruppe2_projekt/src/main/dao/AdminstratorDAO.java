package dao;

import dto.IUserDTO;
import dto.UserDTO;
import exception.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class AdminstratorDAO extends UserDAO implements IAdminstratorDAO {

    //TODO as of 05-05-2019 er denne her præcis den samme som den var i anden jdbc aflevering. Spørgsmålet er om den også skal have noget rolle validering?

    @Override
    public void createUser(IUserDTO user) throws DALException {
        try (Connection c = DataSource.getConnection()){
            c.setAutoCommit(false);
            PreparedStatement usersstatement = c.prepareStatement("INSERT INTO Brugere VALUES (?, ?, ?)");
            usersstatement.setInt(1, user.getUserId());
            usersstatement.setString(2, user.getUserName());
            usersstatement.setString(3, user.getIni());


            for(String role: user.getRoles()) {
                PreparedStatement statementRoles = c.prepareStatement("INSERT INTO Roller VALUES(?,?)");
                statementRoles.setInt(1,user.getUserId());
                statementRoles.setString(2,role);
                statementRoles.executeUpdate();
            }

            int userrows = usersstatement.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public void createUserBasic()throws DALException{
        try(Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            AdminstratorDAO adminUser = new AdminstratorDAO();
            IUserDTO user = new UserDTO();
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter Name:");
            String name = scan.nextLine();
            System.out.println("Enter Initials");
            String ini = scan.nextLine();
            String role = "";
            List<String> roles = new ArrayList<>();
            int choice = 1;

            do {
                System.out.println("Vælg role: \nLaborant - Tryk 1\nPharmaceut - Tryk 2\nProduktionsleder - Tryk 3");
                choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Laborant rolle tilføjet");
                        roles.add("Laborant");
                        break;
                    case 2:
                        System.out.println("Pharmaceut rolle tilføjet");
                        roles.add("Pharmaceut");
                        break;
                    case 3:
                        System.out.println("Produktionsleder rolle tilføjet");
                        roles.add("Produktionsleder");
                        break;
                    default:
                        System.out.println("Ikke en valgmulighed");

                }
            } while (choice < 1 || choice > 3);

            System.out.println("Skal brugeren være administrator?\nJa - Tryk 1\nNej - Tryk 2");
            int admin = scan.nextInt();
            if (admin == 1) {
                System.out.println("Brugeren er nu administrator");
                roles.add("Administrator");
            } else {
                System.out.println("Brugeren er ikke valgt som administrator");
            }

            int brugerID = 0;
            Boolean chosenID = false;

            while(!chosenID) {
                System.out.println("Vælg et 4 cifret bruger ID");
                brugerID = scan.nextInt();
                System.out.println(String.valueOf(brugerID).length());
                if (String.valueOf(brugerID).length() == 4 && brugerID >= 0) {
                    PreparedStatement rolesstatement = c.prepareStatement("SELECT BrugerID FROM Brugere");
                    ResultSet resultSet = rolesstatement.executeQuery();
                    c.commit();
                    //if(resultSet.next()) {
                    int counterUsers = 0;
                    int counterIDs = 0;
                    while (resultSet.next()) {
                        counterUsers++;
                            if (brugerID != resultSet.getInt("BrugerID")) {
                                counterIDs++;
                                }
                            }
                            if(counterIDs != counterUsers){
                                System.out.println("Bruger ID'et er optaget");
                            } else {
                                System.out.println("BrugerID: " + brugerID + " er valgt.");
                                chosenID = true;
                            }
                    //}
                } else {
                    System.out.println("Vælg et 4 cifret bruger ID");
                }
            }
            user.setUserName(name);
            user.setIni(ini);
            user.setRoles(roles);
            user.setUserId(brugerID);
            adminUser.createUser(user);

        }catch (SQLException e) {
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

            PreparedStatement getRolesStatement = c.prepareStatement("DELETE FROM Roller WHERE BrugerID = ?");
            getRolesStatement.setInt(1,user.getUserId());
            getRolesStatement.executeUpdate();

            for(String roles: user.getRoles()){
                PreparedStatement statementRoles = c.prepareStatement("INSERT INTO Roller VALUES(?,?)");
                statementRoles.setInt(1, user.getUserId());
                statementRoles.setString(2,roles);
                statementRoles.executeUpdate();
            }
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

    public void deleteUserBasic() throws DALException {
        try (Connection c = DataSource.getConnection()) {
            c.setAutoCommit(false);
            Scanner scan = new Scanner(System.in);
            AdminstratorDAO adminUser = new AdminstratorDAO();
            System.out.println("Indtast bruger ID for den bruger du ønsker at slette");
            int userID = scan.nextInt();
            PreparedStatement rolesstatement = c.prepareStatement("SELECT brugerNavn FROM Brugere WHERE BrugerID = ?");
            rolesstatement.setInt(1, userID);
            ResultSet resultSet = rolesstatement.executeQuery();
            c.commit();
            String name ="";
            if(resultSet.next()){
                name = resultSet.getString("brugerNavn");
                System.out.println("Er du sikker du vil slette brugeren " + name + "\nJa - Tryk 1\nNej - Tryk 2");
                int deleteChoice = scan.nextInt();
                if(deleteChoice == 1){
                    adminUser.deleteUser(userID);
                    System.out.println(name + " er slettet.");
                } else {
                    System.out.println("Brugeren er ikke slettet");
                }
            } else {
                System.out.println("Brugeren findes ikke");
            }
        } catch (SQLException e){
            throw new DALException(e.getMessage());
        }

    }

    public void changeAdminStatus()throws DALException{

            Scanner scan = new Scanner(System.in);
            AdminstratorDAO adminUser = new AdminstratorDAO();
            System.out.println("Indtast bruger ID for den bruger at ændre admin status for:");
            int userID = scan.nextInt();
            IUserDAO UserDAO = new UserDAO();
            IUserDTO tempuser = UserDAO.getUser(userID);
            if(tempuser.getRoles().size() > 1) {
                System.out.println(tempuser.getUserName() + " er administrator. Vil du fjerne admin privilegier for denne bruger?" +
                        "\nJa - Tryk 1\nNej - Tryk 2");

                int choice = scan.nextInt();
                if (choice == 1) {
                    tempuser.getRoles().remove(0);
                }
            } else {
                    System.out.println(tempuser.getUserName() + " er ikke administrator. Vil du give brugeren administrator privilegier?" +
                            "\nJa - tryk 1\nNej - Tryk 2");
                    int choice = scan.nextInt();
                    if(choice == 1){
                        String s = "Admininstrator";
                        tempuser.getRoles().add(s);
                    }
                }
                adminUser.updateUser(tempuser);

            }


}

