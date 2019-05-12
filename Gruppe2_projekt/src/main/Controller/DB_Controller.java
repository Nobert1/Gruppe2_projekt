package Controller;

import dao.DataSource;
import dao.IUserDAO;
import dao.UserDAO;
import dto.IUserDTO;
import exception.DALException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DB_Controller {

    public void logIn() throws DALException{
        boolean accessGranted = false;
        boolean valid = false;
        int rolleValg = 0;
        do {
            System.out.println("Ønsker du at agere som\nAdministrator tryk 1\nLaborant tryk 2\nPharmaceut tryk 3\nProduktionsleder tryk 4");
            Scanner scan = new Scanner(System.in);
            int rollevalg = scan.nextInt();

            System.out.print("Indtast brugerID\n");
            Scanner scan1 = new Scanner(System.in);
            int brugerID = scan1.nextInt();

            System.out.println(brugerID);

            //TODO: Tjek at brugeren har rettigheder til at agere således

            switch (rollevalg) {
                case 1:
                    // code block
                    System.out.println("Du har valgt administrator");
                    valid = validateUser("Administrators", brugerID);
                    if(valid){}
                    break;
                case 2:
                    // code block
                    System.out.println("Du har valgt laborant");
                    valid = validateUser("Laborants", brugerID);

                    break;
                case 3:
                    // code block
                    System.out.println("Du har valgt pharmaceut");
                    valid = validateUser("Pharmaceuts", brugerID);
                    break;
                case 4:
                    // code block
                    System.out.println("Du har valgt produktionsleder");
                    valid = validateUser("Produktionsledere", brugerID);
                    break;
                default:
                    // code block
                    System.out.println("Det indtastede var ikke et af de fire valg.");
            }

            if(valid){
                accessGranted = true;
            }

        }while(accessGranted);

        //TODO: Hvis godkendt, sæt bruger som tilsvarende DAO.

            switch(rolleValg) {
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
        //TODO: Implementer exit funktion.
    }



    public boolean validateUser(String role, int brugerID) throws DALException {

        boolean validated = false;
        IUserDAO UserDAO = new UserDAO();
        IUserDTO user = UserDAO.getUser(brugerID);


       return validated;
    }
}

 /*try (Connection c = DataSource.getConnection()) {

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
        }*/