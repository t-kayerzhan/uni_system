package users;

import java.util.Date;

public abstract class Employee extends User {
    private double salary;
    private String department;
    private Date hireDate;

    public Employee(String id, String username, String password,
                    String firstName, String lastName, String email,
                    double salary, String department) {
        super(id, username, password, firstName, lastName, email);
        this.salary = salary;
        this.department = department;
        this.hireDate = new Date();
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Date getHireDate() { return hireDate; }

    @Override
    public String toString() {
        return super.toString() + " | Dept: " + department + " | Salary: " + salary;
    }
}
