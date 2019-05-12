package Controller;

import dao.*;
import dto.IProductBatchDTO;
import dto.IUserDTO;
import dto.ProductBatchDTO;
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
    }


    public boolean validateUser(String role, int brugerID) throws DALException {

        boolean validated = false;
        IUserDAO UserDAO = new UserDAO();
        IUserDTO user = UserDAO.getUser(brugerID);
        if (user == null) {
            System.out.println("Ugyldigt UserID.");
        } else {
            for (String s : user.getRoles()) {
                if (s.equals(role)) {
                    System.out.println("Log in Succesfuld");
                    validated = true;
                } else {
                    System.out.println("Du prøver at logge ind som " + role + " som du ikke har adgang til");
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
                System.out.println("Du har ikke administrator privilegie");
            }
        }
        return validated;
    }

    public void admin(AdminstratorDAO user)throws DALException {
       int choice = 0;
        do {
           System.out.println("Du er logget ind som admin\nOpret Bruger - Tryk 1\nSlette bruger - Tryk 2\nÆndre admin rettigheder - Tryk 3" +
                   "\nExit - Tryk 4");
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

    public void Laborant(LaborantDAO user)throws DALException{
       int choice = 0;
       do {
           System.out.println("Du er logget ind som laborant\nSee Produkt Requestst - Tryk 1\nSee aktive produktioner - Tryk 2" +
                   "\nExit - Tryk 3");
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
                System.out.println("Du er logget ind som Pharmaceut\nOpret opskrift - Tryk 1\nSe/rediger opskrifter - Tryk 2" +
                        "\nExit - Tryk 3");
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
                System.out.println("Du er logget ind som Produktionsleder\nSee råvarebatch lager- Tryk 1\nSe/rediger råvarebatch - Tryk 2" +
                        "\nOpret Produktion Request - Tryk 3\nExit - Tryk 4");
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


