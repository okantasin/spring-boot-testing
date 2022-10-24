package com.java.springboot.service;

import com.java.springboot.exception.ResourceNotFoundException;
import com.java.springboot.model.Employee;
import com.java.springboot.repository.EmployeeRepository;
import com.java.springboot.sevice.concretes.EmployeeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeManager employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        //  employeeRepository = Mockito.mock(EmployeeRepository.class);
        //   employeeService = new EmployeeManager(employeeRepository);
        employee = Employee
                .builder()
                .id(1L)
                .firstName("OKAN")
                .lastName("TASIN")
                .email("ok@hotmail.com")
                .build();
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        //given-precondition or setup
        given(employeeRepository
                .findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());


        given(employeeRepository
                .save(employee))
                .willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when-action or the behavior that we are going that,
        Employee savedEmployee = employeeService
                .saveEmployee(employee);

        System.out.println(savedEmployee);


        //then-verify the output
        assertThat(savedEmployee)
                .isNotNull();

    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeeList() {
        //given-precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("ozan")
                .lastName("tasin")
                .email("ozaner@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));


        //when-action or the behavior that we are going that,

        List<Employee> employeeList = employeeService.getAllEmployee();

        //then-verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    //JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method(negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
        //given-precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("ozan")
                .lastName("tasin")
                .email("ozaner@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());


        //when-action or the behavior that we are going that,

        List<Employee> employeeList = employeeService.getAllEmployee();

        //then-verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);

    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmloyeeObject() {
        //given-precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when-action or the behavior that we are going that
        Employee savedEmploee = employeeService.getEmployeeById(employee.getId()).get();

        //then-verify the output
        assertThat(savedEmploee).isNotNull();

    }

    // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ok@gmail.com");
        employee.setFirstName("Ram");
        // when -  action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ok@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}


