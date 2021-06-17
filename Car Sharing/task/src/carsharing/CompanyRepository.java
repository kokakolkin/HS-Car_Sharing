package carsharing;

import carsharing.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompanyRepository {

    private final String connectionUrl;

    public CompanyRepository(String fileName) {
        String filePath = String.format("./src/carsharing/db/%s", fileName);
        this.connectionUrl = String.format("jdbc:h2:%s", filePath);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS COMPANY(" +
                "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR NOT NULL UNIQUE" +
                ")";
        update(sql);

        sql = "CREATE TABLE IF NOT EXISTS CAR (" +
                "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR NOT NULL UNIQUE," +
                "COMPANY_ID INT NOT NULL, " +
                "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                ")";
        update(sql);


        sql = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "NAME VARCHAR NOT NULL UNIQUE," +
                "RENTED_CAR_ID INT, " +
                "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                ")";
        update(sql);

    }

    public void rentCar(int customerId, int carId, String carName) {

        String sql = "UPDATE customer SET rented_car_id = "+ carId +" WHERE id = " + customerId;
        update(sql);
        System.out.println("You rented '" + carName + "'");
        System.out.println();

    }

    public void returnCar(int customerId) {

        String sql = "UPDATE customer SET rented_car_id = NULL WHERE id = " + customerId;
        update(sql);
        System.out.println("You've returned a rented car!");
        System.out.println();

    }

    public void addCompany() {
        System.out.println("Enter the company name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        String sql = "INSERT INTO company (name) VALUES ('"+ name +"')";
        update(sql);
        System.out.println("The company was created!");
        System.out.println();

    }

    public void addCar(int company_id) {
        System.out.println("Enter the car name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String sql = "INSERT INTO CAR (name, company_id) VALUES ('"+ name +"'," + company_id + ")";
        update(sql);
        System.out.println("The car was added!");
        System.out.println();

    }

    public void addCustomer() {
        System.out.println("Enter the customer name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String sql = "INSERT INTO CUSTOMER (NAME,RENTED_CAR_ID) VALUES ('"+ name +"',NULL)";
        update(sql);
        System.out.println("The customer was added!");
        System.out.println();

    }




    public void getAllCompanies(int customerId) {

        String sql = "SELECT * FROM COMPANY ORDER BY ID";
        List<Company> list = new ArrayList<Company>();

        int i = 1;
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (PreparedStatement stm = conn.prepareStatement(sql)) {
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    if (i == 1) System.out.println("Choose the company:");
                    Company c = new Company();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    list.add(c);
                    System.out.println(i + ". " + rs.getString("name"));
                    i++;
                }
                if (i > 1) {
                    System.out.println("0. Back");
                    Scanner scanner = new Scanner(System.in);
                    String company_id = "1";
                    boolean b = true;
                    while (b) {
                         company_id = scanner.nextLine();
                        try
                    {
                        int cId = Integer.parseInt(company_id);
                        // System.out.println(cId + " =c, i= " + i);
                        b = cId < 0 || cId > i-1 ?  true : false;
                        //System.out.println(cId + " =c, i= " + i + " " + b);
                    }
                    catch (NumberFormatException e)
                    {
                        b = true;

                    }
                    }
                    if (Integer.parseInt(company_id) != 0 ) {
                    Company company = list.get(Integer.parseInt(company_id) - 1);
                    String companyName = company.getName();

                    if (customerId < 0)
                        carList(Integer.parseInt(company_id), companyName);
                    else
                        getAllCars(Integer.parseInt(company_id), companyName,customerId);

                     }
                }
                else {
                    //System.out.println("i7:" + i);
                    if ( i == 1 )  System.out.println("The company list is empty!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void getAllCustomers() {

        String sql = "SELECT * FROM CUSTOMER ORDER BY ID";
        List<Customer> list = new ArrayList<Customer>();

        int i = 1;
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (PreparedStatement stm = conn.prepareStatement(sql)) {
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    if (i == 1) System.out.println("Customer list:");
                    Customer c = new Customer();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setRentedCarId(rs.getInt("rented_car_id"));
                    list.add(c);
                    System.out.println(i + ". " + rs.getString("name"));
                    i++;
                }
                if (i > 1) {
                    System.out.println("0. Back");
                    Scanner scanner = new Scanner(System.in);
                    String c_id = "1";
                    boolean b = true;
                    while (b) {
                        c_id = scanner.nextLine();
                        try
                        {
                            int cId = Integer.parseInt(c_id);
                            // System.out.println(cId + " =c, i= " + i);
                            b = cId < 0 || cId > i-1 ?  true : false;
                            //System.out.println(cId + " =c, i= " + i + " " + b);
                        }
                        catch (NumberFormatException e)
                        {
                            b = true;
                        }
                    }
                    if (Integer.parseInt(c_id) != 0 ) {
                        Customer customer = list.get(Integer.parseInt(c_id) - 1);
                        String customerName = customer.getName();
                        customerActions(Integer.parseInt(c_id), customerName);
                    }
                }
                else {
                    if ( i == 1 )  System.out.println("The customer list is empty!");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void getAllCars(int company_id, String companyName, int customerId){
        String sql = "SELECT car.id as id, car.name as name, car.company_id as company, "
                + "customer.rented_car_id as rented_car_id, customer.id as customer_id"
                + " FROM car  "
                + " LEFT JOIN customer ON car.id = customer.rented_car_id "
                + " WHERE company_id = " + company_id
                //+ " AND car.id <> customer.rented_car_id "
                + " ORDER BY ID";
        List<Car> list = new ArrayList<Car>();
        int i = 1;
        int j = 0;
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (PreparedStatement stm = conn.prepareStatement(sql)) {
                ResultSet rs = stm.executeQuery();
                if (customerId > 0) {
                    while (rs.next()) {
                        if (rs.getString("customer_id") == null ) {
                            if ( j == 0 )  System.out.println("'" + companyName + "' company:");
                            Car c = new Car();
                            c.setId(rs.getInt("id"));
                            c.setName(rs.getString("name"));
                            c.setCompanyId(rs.getInt("company_id"));
                            list.add(c);
                            System.out.println(i  + ". " + rs.getString("name"));
                            i++;
                            j++;
                        }

                    }
                    if ( j > 0) {
                        System.out.println("0. Back");
                        Scanner scanner = new Scanner(System.in);
                        int choice = scanner.nextInt();
                        if (choice != 0 )
                        rentCar(customerId, list.get(choice - 1).getId(), list.get(choice - 1).getName());

                    }
                }

                else {
                    while (rs.next()) {
                        if ( i == 1 )  System.out.println("'" + companyName + "' company:");
                            System.out.println(i  + ". " + rs.getString("name"));
                            i++;


                    }

                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ( i == 1 )  System.out.println("The car list is empty!");
        System.out.println();
    }

    public void showRentedCar(int customerId){
        String sql = "SELECT count(*) as c, customer.id, customer.rented_car_id as rentedCarId, car.name as carName, company.name as companyName "
                + " FROM customer "
                + " LEFT JOIN car on customer.rented_car_id = car.id "
                + " LEFT JOIN company on car.company_id = company.id "
                + " WHERE customer.id =" + customerId;
                //+ " AND customer.rented_car_id IS NOT NULL";
        boolean noCar = true;
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (PreparedStatement stm = conn.prepareStatement(sql)) {
                ResultSet rs = stm.executeQuery();
                rs.next();
                //System.out.println("name: " + rs.getString("carName"));
                if (rs.getString("carName") != null ) {
                    System.out.println("Your rented car:");
                    System.out.println(rs.getString("carName"));
                    System.out.println("Company:");
                    System.out.println(rs.getString("companyName"));
                    noCar = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (noCar)  System.out.println("You didn't rent a car!");
        System.out.println();
    }


    private void carList(int company_id, String company_name) {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;
        while (!back) {
            System.out.println("'" + company_name + "' company") ;
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    getAllCars(company_id, company_name, -1);
                    break;
                case 2:
                    addCar(company_id);
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void customerActions(int customer_id, String customerName) {
        Scanner scanner = new Scanner(System.in);
        boolean back = false;
        while (!back) {
//            System.out.println("'" + company_name + "' company") ;
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");

            int userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    if ( !isRented(customer_id))
                    getAllCompanies(customer_id);
                    else System.out.println("You've already rented a car!");
                    break;
                case 2:
                    if ( isRented(customer_id))
                        returnCar(customer_id);
                    else System.out.println("You didn't rent a car!");
                    break;
                case 3:
                    showRentedCar(customer_id);
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void update(String sql) {
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (Statement statement = conn.createStatement()) {
//System.out.println(sql);
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private ResultSet select(String sql)  {
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (Statement statement = conn.createStatement()) {
                return statement.executeQuery(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean isRented(int customer_id)  {
        String sql = "SELECT count(*) as c FROM customer WHERE id = " + customer_id + " AND rented_car_id IS NOT NULL";
        //System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(true);
            try (Statement statement = conn.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);
                rs.next();
                //System.out.println("counter:" +rs.getInt("c"));
                if (rs.getInt("c") > 0) return true;
                else return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}