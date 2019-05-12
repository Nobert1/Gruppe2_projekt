package dao;

import dto.IUserDTO;
import dto.UserDTO;
import exception.DALException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminstratorDAOTest {


    @Test
    void deleteUserBasic() throws DALException {

        //Opretter en test User
        IUserDTO testUser = new UserDTO();
        AdminstratorDAO admin = new AdminstratorDAO();
        testUser.setUserId(1234);
        testUser.setUserName("Per Hansen");
        testUser.setIni("PH");
        ArrayList<String> roles = new ArrayList();
        roles.add("Administrator");
        testUser.setRoles(roles);
        admin.deleteUser(1234);
        admin.createUser(testUser);


        //Sletter med brug af metoden
        ByteArrayInputStream in = new ByteArrayInputStream(("1234" + System.lineSeparator() + "1").getBytes());
        System.setIn(in);
        admin.deleteUserBasic();
        System.setIn(System.in);

        //Tester om brugeren stadig er i databasen
        List<IUserDTO> allUsers = admin.getUserList();
        boolean found = false;
        for (IUserDTO user : allUsers) {
            if(user.getUserId() == testUser.getUserId()){
                found = true;
            }
        }
        assertTrue(!found);


    }

    @Test
    void createUserBasic() throws DALException {
        AdminstratorDAO admin = new AdminstratorDAO();
        admin.deleteUser(1234);
//Opretter brugeren
        ByteArrayInputStream in = new ByteArrayInputStream(("Per Hansen" + System.lineSeparator() + "PH" + System.lineSeparator() +
                "1" + System.lineSeparator() + "1" + System.lineSeparator() + "1234").getBytes());
        System.setIn(in);
        admin.createUserBasic();
        System.setIn(System.in);
       //Tester om brugeren er i databasen
        List<IUserDTO> allUsers = admin.getUserList();
        boolean found = false;
        for (IUserDTO user : allUsers) {
            if(user.getUserId() == 1234){
                found = true;
            }
        }
        assertTrue(found);

    }


    @Test
    void changeAdminStatus()throws DALException {
        //Opretter en test User
        IUserDTO testUser = new UserDTO();
        AdminstratorDAO admin = new AdminstratorDAO();
        testUser.setUserId(1234);
        testUser.setUserName("Per Hansen");
        testUser.setIni("PH");
        ArrayList<String> roles = new ArrayList();
        roles.add("Administrator");
        roles.add("Laborant");
        testUser.setRoles(roles);
        admin.deleteUser(1234);
        admin.createUser(testUser);

        //Fjerner test user administrator privilegie
        ByteArrayInputStream in = new ByteArrayInputStream(("1234" + System.lineSeparator() + "1").getBytes());
        System.setIn(in);
        admin.changeAdminStatus();
        System.setIn(System.in);

        //Tester om rolen er fjernet
        testUser = admin.getUser(1234);
        Boolean Administrator = true;
        for(String s: testUser.getRoles()){
            if(s == "Administrator"){
                Administrator = false;
            }
        }
        assertTrue(Administrator);
    }
}