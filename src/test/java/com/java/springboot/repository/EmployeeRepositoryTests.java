package com.java.springboot.repository;

import com.java.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup(){
        employee=Employee.builder()
                .firstName("okaner")
                .lastName("ıgdır")
                .email("okaner@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whensave_thenReturnSaveEmployee() {

        //given-procondition or setup
        Employee employee = Employee.builder()
                .firstName("Mode")
                .lastName("XL")
                .email("modexl@hotmail.com")
                .build();

        //when-action or the behavior that we are going that
        Employee saveEmployee = employeeRepository.save(employee);

        //then-verify the output
        Assertions.assertThat(saveEmployee).isNotNull();
        Assertions.assertThat(saveEmployee.getId()).isGreaterThan(0);
    }

    //JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {
        //given- proconditional or setup
        Employee employee = Employee.builder()
                .firstName("Spring")
                .lastName("boot")
                .email("springboot@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("apache")
                .lastName("kafka")
                .email("kafka@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when-action or the behavior that we are going that
        List<Employee> employeesList = employeeRepository.findAll();

        //then-verify the output

        Assertions.assertThat(employeesList).isNotNull();
        Assertions.assertThat(employeesList.size()).isEqualTo(2);
    }

    //JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenEmployeeObject_whenFindById_then() {
        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Apache")
                .lastName("Camel")
                .email("camel@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when-action or the behavior that we are going that
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();
        //then-verify the output
        Assertions.assertThat(employeeDb).isNotNull();

    }

    //JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenRturnEmployeeObject() {
        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Yapma")
                .lastName("Serkan")
                .email("yalvarırımcek@gmail.com")
                .build();

        employeeRepository.save(employee);
        //when-action or the behavior that we are going that,
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail()).get();
        //then-verify the output
        Assertions.assertThat(employeeDb).isNotNull();

    }

    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("hergidisin")
                .lastName("birdedonusuvarsa")
                .email("sevdigim@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when-action or the behavior that we are going that
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setFirstName("uyan");
        savedEmployee.setEmail("oc@gmail.com");
        Employee updateEmployee = employeeRepository.save(savedEmployee);

        //then-verify the output
        Assertions.assertThat(updateEmployee.getEmail()).isEqualTo("oc@gmail.com");
        Assertions.assertThat(updateEmployee.getFirstName()).isEqualTo("uyan");

    }

    //JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        //given-precondition or setup

        Employee employee = Employee.builder()
                .firstName("Yapma")
                .lastName("Serkan")
                .email("yalvarırımcek@gmail.com")
                .build();

        employeeRepository.save(employee);

        //when-action or the behavior that we are going that

        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then-verify the output

        Assertions.assertThat(employeeOptional).isEmpty();

    }

    //JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Hey")
                .lastName("Dougles")
                .email("veyasin@gmail.com")
                .build();

        employeeRepository.save(employee);
        String firstName = "Hey";
        String lastName = "Dougles";
        //when-action or the behavior that we are going that
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
        //then-verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for custom query using JPQL with name params
    @DisplayName("JUnit test for custome query using JPQL with name")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNameParams_thenReturnEmployeeObject() {

        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Hey")
                .lastName("Dougles")
                .email("veyasin@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Hey";
        String lastName = "Dougles";

        //when-action or the behavior that we are going that,
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then-verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Hey")
                .lastName("Dougles")
                .email("veyasin@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when-action or the behavior that we are going that,
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then-verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    //JUnit test for custom query using native SQL with name params
    @DisplayName("JUnit test for custom query using native SQL with name params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNameParams_thenReturnEmployeeObject() {
        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Hey")
                .lastName("Dougles")
                .email("veyasin@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when-action or the behavior that we are going that,
        Employee savedEmployee = employeeRepository.findByJPQLNameParams(employee.getFirstName(), employee.getLastName());

        //then-verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

}
