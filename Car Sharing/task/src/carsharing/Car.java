package carsharing;

public class Car {
    private int id;
    private String name;
    private int companyId;


    public Car() {
        int id;
        String name;
        int companyId;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getCompanyId() {
        return companyId;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


}
