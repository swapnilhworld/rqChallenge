package com.example.rqchallenge.employees;

import com.example.rqchallenge.constants.CommonConstants;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    RestTemplate restTemplate = new RestTemplate();

    @Value("${api.base.url}")
    private String API_BASE_URL;

    @Override
    public List<Employee> getAllEmployees() throws IOException {

        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/employees", String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = new ArrayList<>();

        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");
        employees = mapper.readerFor(new TypeReference<List<Employee>>() {
        }).readValue(data);

        return employees;
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) throws IOException {

        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/employees", String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Employee> employees = null;

        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");
        employees = mapper.readerFor(new TypeReference<List<Employee>>() {
        }).readValue(data);

        return employees.stream().filter(element -> element.getEmployee_name().contains(searchString))
                .collect(Collectors.toList());

    }

    @Override
    public Employee getEmployeeById(String id) throws IOException {

        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/employee/" + id, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Employee employee = null;
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");

        employee = mapper.readerFor(new TypeReference<Employee>() {
        }).readValue(data);

        return employee;
    }

    @Override
    public Integer getHighestSalaryOfEmployees() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/employees", String.class);

        ObjectMapper mapper = new ObjectMapper();
        Integer salary = 0;
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");
        List<Employee> employee = mapper.readerFor(new TypeReference<List<Employee>>() {
        }).readValue(data);

        Optional<Employee> emp = employee.stream()
                .sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed()).findFirst();

        salary = emp.get().getEmployee_salary();

        return salary;
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() throws IOException {
        ResponseEntity<String> response = restTemplate.getForEntity(API_BASE_URL + "/employees", String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<String> emp = null;
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");
        List<Employee> employee = mapper.readerFor(new TypeReference<List<Employee>>() {
        }).readValue(data);

        emp = employee.stream().sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed()).limit(10)
                .map(Employee::getEmployee_name).collect(Collectors.toList());

        return emp;
    }

    @Override
    public String createEmployee(Employee employeeInput) throws IOException {

        ResponseEntity<String> response = restTemplate.postForEntity(API_BASE_URL + "/create", employeeInput,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode status = root.path("status");

        String responseStatus = mapper.readerFor(new TypeReference<String>(){}).readValue(status);

        if (responseStatus.equalsIgnoreCase("success")) {
            return responseStatus;
        } else {
            return null;
        }
    }

    @Override
    public String deleteEmployeeById(String id) throws IOException {

        Employee employee = this.getEmployeeById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String response = restTemplate.exchange(API_BASE_URL + "/delete/" + id, HttpMethod.DELETE, entity, String.class)
                .getBody();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response);
        JsonNode status = root.path("status");

        String responseStatus = mapper.readerFor(new TypeReference<String>() {
        }).readValue(status);

        if (responseStatus.equalsIgnoreCase(CommonConstants.SUCCESS)) {
            log.info(EmployeeConstants.DELETED );
            return employee.getEmployee_name();
        } else {
            log.error(EmployeeConstants.DELETE_FAILED );
            return null;
        }

    }


}
