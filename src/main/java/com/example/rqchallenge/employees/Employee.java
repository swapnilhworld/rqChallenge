package com.example.rqchallenge.employees;


public class Employee {
    private Integer id;
    private String employee_name;
    private Integer employee_salary;
    private Integer employee_age;
    private String profile_image;

    public Employee() {
        super();
    }

    public Employee(Integer id, String employee_name, Integer employee_salary, Integer employee_age,
                    String profile_image) {
        super();
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.profile_image = profile_image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Integer getEmployee_salary() {
        return employee_salary;
    }

    public void setEmployee_salary(Integer employee_salary) {
        this.employee_salary = employee_salary;
    }

    public Integer getEmployee_age() {
        return employee_age;
    }

    public void setEmployee_age(Integer employee_age) {
        this.employee_age = employee_age;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", employee_name=" + employee_name + ", employee_salary=" + employee_salary + ", employee_age=" + employee_age+ ", profile_image=" + profile_image +"]";
    }
}
