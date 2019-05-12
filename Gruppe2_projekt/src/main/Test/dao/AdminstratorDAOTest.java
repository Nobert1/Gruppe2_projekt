package dao;

import dto.IUserDTO;
import dto.UserDTO;
import exception.DALException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AdminstratorDAOTest {

    @Test
    void createUser() {
    }

    @Test
    void createUserBasic() throws DALException {
        AdminstratorDAO admin = new AdminstratorDAO();
        IUserDTO testUser = new UserDTO();
        testUser.setUserId(13);
        testUser.setUserName("Per Hansen");
        testUser.setIni("PH");
        ArrayList<String> roles = new ArrayList();
        roles.add("Administrator");
        testUser.setRoles(roles);
        admin.deleteUser(13);
        admin.createUser(testUser);

        ByteArrayInputStream in = new ByteArrayInputStream(("Alex a" + System.lineSeparator() + "AA" + System.lineSeparator() +
                "1" + System.lineSeparator() + "1" + System.lineSeparator() + "2222").getBytes());
        System.setIn(in);
        admin.createUserBasic();
        System.setIn(System.in);

    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {

    }

    @Test
    void deleteUserBasic() throws DALException{
        AdminstratorDAO admin = new AdminstratorDAO();
        ByteArrayInputStream in = new ByteArrayInputStream(("2222" + System.lineSeparator() + "1").getBytes());
        System.setIn(in);
        admin.deleteUserBasic();
        System.setIn(System.in);

    }

    @Test
    void changeAdminStatus()throws DALException {
        AdminstratorDAO admin = new AdminstratorDAO();
        ByteArrayInputStream in = new ByteArrayInputStream(("2222" + System.lineSeparator() + "1").getBytes());
        System.setIn(in);
        admin.changeAdminStatus();
        System.setIn(System.in);
    }
}