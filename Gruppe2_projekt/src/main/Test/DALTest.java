package Test;

//TODO: This is dev branch.

import dao.*;
import dto.*;
import exception.*;
import org.apache.catalina.User;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALTest {
    //TODO: Indsæt din egen implementering
    IAdminstratorDAO userDAO = new AdminstratorDAO();
    @Test
    public void userTest() throws DALException{
        try {

            IUserDTO testUser = new userDTO();
            testUser.setUserId(13);
            testUser.setUserName("Per Hansen");
            testUser.setIni("PH");
            ArrayList<String> roles = new ArrayList();
            roles.add("administrator");
            testUser.setRoles(roles);
            userDAO.createUser(testUser);
            IUserDTO receivedUser = userDAO.getUser(13);
            assertEquals(testUser.getUserName(),receivedUser.getUserName());
            assertEquals(testUser.getIni(), receivedUser.getIni());
            assertEquals(testUser.getRoles().get(0),receivedUser.getRoles().get(0));
            assertEquals(testUser.getRoles().size(),receivedUser.getRoles().size());
            List<IUserDTO> allUsers = userDAO.getUserList();
            boolean found = false;
            for (IUserDTO user: allUsers) {
                if(user.getUserId() == testUser.getUserId()){
                    assertEquals(testUser.getUserName(),user.getUserName());
                    assertEquals(testUser.getIni(), user.getIni());
                    assertEquals(testUser.getRoles().get(0),user.getRoles().get(0));
                    assertEquals(testUser.getRoles().size(),user.getRoles().size());
                    found = true;
                }
            }
            if(!found){fail();}

            testUser.setUserName("Per petersen");
            testUser.setIni("PP");
            roles.remove(0);
            roles.add("pedel");
            testUser.setRoles(roles);
            userDAO.updateUser(testUser);

            receivedUser = userDAO.getUser(13);
            assertEquals(testUser.getUserName(),receivedUser.getUserName());
            assertEquals(testUser.getIni(), receivedUser.getIni());
            assertEquals(testUser.getRoles().get(0),receivedUser.getRoles().get(0));
            assertEquals(testUser.getRoles().size(),receivedUser.getRoles().size());

            userDAO.deleteUser(testUser.getUserId());
            allUsers = userDAO.getUserList();

            for (IUserDTO user: allUsers) {
                if(user.getUserId() == testUser.getUserId()){
                    fail();
                }
            }

        } catch (DALException e) {
            e.getMessage();
            fail();
        }

    }



    @Test
    public void LaborantTest() throws DALException {
        try {




    } catch (DALException e) {
        e.getMessage();
        fail();
    }
    }

    /**
    @Test
    public void Pharmaceuttest() throws DALException {
        try {




        } catch (DALException e) {
            e.getMessage();
            fail();
        }
    }


    @Test
    public void ProduktionslederTest() throws DALException {
        try {




        } catch (DALException e) {
            e.getMessage();
            fail();
        }
    }*
     * @throws DALException
     */
}
