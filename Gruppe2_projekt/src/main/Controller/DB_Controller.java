package Controller;

import dao.*;
import dto.IUserDTO;
import dto.UserDTO;
import exception.DALException;

import java.util.ArrayList;
import java.util.Scanner;

public class DB_Controller {

    public void logIn() throws DALException {
        boolean accessGranted = false;
        boolean valid = false;
        int rolleValg = 0;

//TODO: translate to danish lol
        do {
            System.out.println("Ønsker du at agere som\nAdministrator - tryk 1\nLaborant - tryk 2\nPharmaceut - tryk 3\nProduktionsleder - tryk 4" +
                    "\nExit - Tryk 5");
            Scanner scan = new Scanner(System.in);
            rolleValg = scan.nextInt();

            System.out.print("Indtast brugerID\n");
            Scanner scan1 = new Scanner(System.in);
            int brugerID = scan1.nextInt();

            System.out.println(brugerID);

            //TODO: Tjek at brugeren har rettigheder til at agere således

            switch (rolleValg) {
                case 1:
                    System.out.println("Du har valgt administrator");
                    valid = validateAdmin(brugerID);
                    if (valid) {
                        AdminstratorDAO user = new AdminstratorDAO();
                        admin(user);
                    }
                    break;
                case 2:
                    System.out.println("Du har valgt laborant");
                    valid = validateUser("Laborant", brugerID);
                    if (valid) {
                        LaborantDAO user = new LaborantDAO();
                        Laborant(user);
                    }
                    break;
                case 3:
                    System.out.println("Du har valgt pharmaceut");
                    valid = validateUser("Pharmaceut", brugerID);
                    if (valid) {
                        PharamaceutDAO user = new PharamaceutDAO();
                        Pharmaceut(user);
                    }
                    break;
                case 4:
                    System.out.println("Du har valgt produktionsleder");
                    valid = validateUser("Produktionsleder", brugerID);
                    if (valid) {
                        ProduktionsLederDAO user = new ProduktionsLederDAO();
                        Produktionsleder(user);
                    }
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Det indtastede var ikke et af de fire valg.");
            }

            if (valid) {
                accessGranted = true;
            }

        } while (!accessGranted);

        //TODO: Hvis godkendt, sæt bruger som tilsvarende DAO.

          /*  switch(rolleValg) {
                case 1:
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
            }*/
        //TODO: Implementer exit funktion.
    }


    public boolean validateUser(String role, int brugerID) throws DALException {

        boolean validated = false;
        IUserDAO UserDAO = new UserDAO();
        IUserDTO user = UserDAO.getUser(brugerID);
        if (user == null) {
            System.out.println("Incorrect UserID.");
        } else {
            for (String s : user.getRoles()) {
                if (s.equals(role)) {
                    System.out.println("Log in Successful");
                    validated = true;
                } else {
                    System.out.println("You are trying to log in as " + role + " which you do not have access to.");
                }
            }
        }
        return validated;
    }

    public boolean validateAdmin(int brugerID) throws DALException {
        boolean validated = false;
        IUserDAO UserDAO = new UserDAO();
        IUserDTO user = UserDAO.getUser(brugerID);
        if (user == null) {
            System.out.println("Incorrect UserID.");
        } else {
            for (String s : user.getRoles()) {
                String s1 = s.toLowerCase();
                if (s1.equals("administrator")) {
                    System.out.println("Log in successful");
                    validated = true;
                }
            }
            if (!validated) {
                System.out.println("You do not have administrator authority.");
            }
        }
        return validated;
    }

    public void admin(AdminstratorDAO user)throws DALException {
       int choice = 0;
        do {
           System.out.println("You are logged in as Admin.\nCreate user - Press 1\nDelete user - Press 2\nChange admin rights for user - press 3" +
                   "\nTo exit - Press 4");
           Scanner scan = new Scanner(System.in);
           choice = scan.nextInt();
           switch (choice) {
               case 1:
                   user.createUserBasic();
                   break;
               case 2:
                   user.deleteUserBasic();
                   break;
               case 3:
                   user.changeAdminStatus();
                   break;
               default:


           }
       }while(choice!=4);

    }

    public void Laborant(LaborantDAO user) {
       int choice = 0;
       do {
           System.out.println("You are logged in as Laborant\nSee production Requests - Press 1\nSee active production - Press 2" +
                   "\nTo exit - Press 3");
           Scanner scan = new Scanner(System.in);
           choice = scan.nextInt();
           switch (choice) {
               case 1:

               case 2:

               default:


           }
       }while(choice!=3);
    }


        public void Pharmaceut(PharamaceutDAO user){
            int choice = 0;
            do {
                System.out.println("You are logged in as Pharmaceut\nCreate new recipe - Press 1\nSee/edit recipes - Press 2" +
                        "\nTo exit - Press 3");
                Scanner scan = new Scanner(System.in);
                choice = scan.nextInt();
                switch (choice) {
                    case 1:

                    case 2:

                    default:
                }
            }while(choice != 3);

        }

        public void Produktionsleder (ProduktionsLederDAO user) {
            int choice = 0;
            do {
                System.out.println("You are logged in asd Produktionsleder\nSee commodity storage - Press 1\nCreate/Edit Commodity batch - Press 2" +
                        "\nCreate Produktion Request - Press 3\nTo exit - Press 4");
                Scanner scan = new Scanner(System.in);
                choice = scan.nextInt();
                switch (choice) {
                    case 1:

                    case 2:

                    case 3:

                    default:
                }
            }while(choice != 4);
        }
}


