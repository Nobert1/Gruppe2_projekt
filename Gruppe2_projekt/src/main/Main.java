import dao.DataSource;
import dto.IUserDTO;
import exception.DALException;
import java.util.*;
import java.sql.*;

public class Main {

    public static void main (String [ ] args) throws DALException{

        Scanner scan = new Scanner(System.in);

        System.out.print("Indtast brugerID");

        String brugerID = scan.nextLine();

        validateUser(brugerID);

        //System.out.println("Ønsker du at agere som admin tryk 1, lab tryk 2, farma tryk 3 og leder tryk 4");

        //TODO: Tjek at brugeren har rettigheder til at agere således

        //TODO: Hvis godkendt, sæt bruger som tilsvarende DAO.

        //TODO: Implementer exit funktion.

    }

    private static boolean validateUser(String brugerID) throws DALException {

        boolean validated = false;

        try (Connection c = DataSource.getConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BrugerID FROM Bruger WHERE BrugerID = " + brugerID);
            if (resultSet.toString() == brugerID) {
                validated = true;
                System.out.println("Brugeren er valideret.");
            } else {
                System.out.println("Brugeren er ikke valideret.");
            }
            return validated;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

}
