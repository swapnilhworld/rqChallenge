package com.example.rqchallenge.employees;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees() throws IOException;

    List<Employee> getEmployeesByNameSearch(String searchString) throws IOException;

    Employee getEmployeeById( String id)throws IOException;

    Integer getHighestSalaryOfEmployees()throws IOException;

    List<String> getTopTenHighestEarningEmployeeNames()throws IOException;

    String createEmployee( Employee employeeInput)throws IOException;

    String deleteEmployeeById( String id)throws IOException;
}
