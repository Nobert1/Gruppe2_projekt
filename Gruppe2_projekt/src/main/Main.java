import dao.DataSource;
import dto.IUserDTO;
import exception.DALException;
import java.util.*;
import java.sql.*;

public class Main {

    public static void main (String [ ] args) throws DALException{

        System.out.println("Ønsker du at agere som\nAdministrator tryk 1\nLaborant tryk 2\nPharmaceut tryk 3\nProduktionsleder tryk 4");

        Scanner scan = new Scanner(System.in);

        boolean valid = false;

        int rollevalg = scan.nextInt();

        System.out.print("Indtast brugerID\n");

        Scanner scan1 = new Scanner(System.in);

        String brugerID = scan1.nextLine();

        System.out.printf(brugerID);

        //TODO: Tjek at brugeren har rettigheder til at agere således

        switch(rollevalg) {
            case 1:
                // code block
                System.out.println("Du har valgt administrator");
                validateUser("Administrators", brugerID);
                break;
            case 2:
                // code block
                System.out.println("Du har valgt laborant");
                validateUser("Laborants", brugerID);
                break;
            case 3:
                // code block
                System.out.println("Du har valgt pharmaceut");
                validateUser("Pharmaceuts", brugerID);
                break;
            case 4:
                // code block
                System.out.println("Du har valgt produktionsleder");
                validateUser("Produktionsledere", brugerID);
                break;
            default:
                // code block
                System.out.println("Det indtastede var ikke et af de fire valg.");
                main(args);
        }

        //TODO: Hvis godkendt, sæt bruger som tilsvarende DAO.

        if (valid) {

            switch(rollevalg) {
                case 1:
                    // code block
                    System.out.printf("Valg for admin");
                    break;
                case 2:
                    // code block
                    System.out.printf("Valg for laborant");
                    break;
                case 3:
                    // code block
                    System.out.printf("Valg for pharmaceut");
                    break;
                case 4:
                    // code block
                    System.out.printf("Valg for produktionsleder");
                    break;
                default:
                    // code block
            }

        }

        //TODO: Implementer exit funktion.

    }

    private static boolean validateUser(String tabel, String brugerID) throws DALException {

        boolean validated = false;

        try (Connection c = DataSource.getConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BrugerID FROM "+tabel+" WHERE BrugerID = " + brugerID);

            String output = "";

            if (resultSet.next()) {
                output = resultSet.getString("BrugerID");
            }

            if (output.equals(brugerID)) {
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
