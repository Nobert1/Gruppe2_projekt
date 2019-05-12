import Controller.DB_Controller;
import dao.DataSource;
import dto.IUserDTO;
import exception.DALException;
import java.util.*;
import java.sql.*;

public class Main {

    public static void main (String [ ] args) throws DALException {
        DB_Controller controller = new DB_Controller();
        controller.logIn();
    }

}
