package com.java.springboot.controller;

import com.java.springboot.model.Employee;
import com.java.springboot.sevice.abstracts.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee savedEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        return employeeService.getEmployeeById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
                                                   @RequestBody Employee employee){
        return employeeService.getEmployeeById(employeeId).map(savedEmployee->{
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setEmail(employee.getEmail());
            Employee updateEmployee=employeeService.updateEmployee(savedEmployee);
            return  new ResponseEntity<>(updateEmployee,HttpStatus.OK);
        })
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId){

        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);

    }


}
