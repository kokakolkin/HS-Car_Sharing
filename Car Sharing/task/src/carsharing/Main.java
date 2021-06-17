package carsharing;

import carsharing.CompanyRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName;
        if (args.length >= 2 && args[0].equals("-databaseFileName")) {
            fileName = args[1];
        } else {
            fileName = "data";
        }
        CompanyRepository repository = new CompanyRepository(fileName);
        repository.createTable();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");


            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    managerLogin(repository);
                    break;
                case 2:
                    repository.getAllCustomers();
                    break;
                case 3:
                    repository.addCustomer();
                    break;
                case 0:
//                    System.out.println("Bye!");
                    System.exit(0);
            }
        }
    }

    static void managerLogin(CompanyRepository repository) {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;
        while (!back) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");

            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    repository.getAllCompanies(-1);
                    break;
                case 2:
                    repository.addCompany();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }





}