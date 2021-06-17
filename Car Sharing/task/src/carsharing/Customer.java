package carsharing;

public class Customer {
    private int id;
    private String name;
    private int rentedCarId;


    public Customer() {
        int id;
        String name;
        int rentedCarId;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getRentedCarIdId() {
        return rentedCarId;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }


}
