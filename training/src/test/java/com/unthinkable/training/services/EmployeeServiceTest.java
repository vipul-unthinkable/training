/**
 * 
 */
package com.unthinkable.training.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.unthinkable.training.dao.DepartmentRepository;
import com.unthinkable.training.dao.EmployeeRepository;
import com.unthinkable.training.entities.Department;
import com.unthinkable.training.entities.Employee;

/**
 * @author tushar01
 *
 */
class EmployeeServiceTest {
	
	@Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;


    private EmployeeService employeeService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		employeeService=new EmployeeService(employeeRepository);
		employeeService.setDepartmentRepository(departmentRepository);
	}

	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#getAllEmployees()}.
	 */
	@Test
	void testGetAllEmployees() {
		// Arrange
		List<Employee> expectedEmployees=new ArrayList<>();
    	Set<Department> departments=new HashSet<>();
    	Department department =new Department();
    	department.setCreatedDate(LocalDateTime.now());
    	department.setId(1);
    	department.setLastModifiedDate(LocalDateTime.now());
    	department.setName("IT");
    	departments.add(department);
    	
    	Employee emp=new Employee();
    	emp.setCity("city");
    	emp.setCreatedDate(LocalDateTime.now());
    	emp.setDepartments(departments);
    	emp.setExperience(2);
    	emp.setId(1);
    	emp.setLastModifiedDate(LocalDateTime.now());
    	emp.setMobileNumber("mobile number");
    	emp.setName("name");
    	emp.setState("state");
    	expectedEmployees.add(emp);
        Mockito.when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        // Act
        List<Employee> actualEmployees = employeeService.getAllEmployees();

        // Assert
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertArrayEquals(expectedEmployees.toArray(), actualEmployees.toArray());

	}

	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#getEmployeeById(int)}
	 * when employee exists.
	 */
	@Test
	void testGetEmployeeById_WhenEmployeeExists() {
		 // Arrange
        int employeeId = 1;
        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(employeeId);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(expectedEmployee));

        // Act
        Employee actualEmployee = employeeService.getEmployeeById(employeeId);

        // Assert
        assertEquals(expectedEmployee, actualEmployee);	
      }
	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#getEmployeeById(int)}
	 * when employee does not exists.
	 */
	@Test
	void testGetEmployeeById_WhenEmployeeNotExists() {
		 // Arrange
        int employeeId = 1;

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act
        Employee actualEmployee = employeeService.getEmployeeById(employeeId);

        // Assert
        assertNull(actualEmployee);	
      }

	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#addEmployees(java.util.List)}.
	 */
	@Test
	void testAddEmployees() {
		// Arrange
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        Set<Department> departments = new HashSet<>(Arrays.asList(new Department(), new Department()));
        employees.get(0).setDepartments(departments);
        employees.get(1).setDepartments(departments);

        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(new Department());
        Mockito.when(employeeRepository.saveAll(employees)).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.addEmployees(employees);

        // Assert
        assertNotNull(result);
        assertEquals(employees.size(), result.size());	}

	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#updateEmployee(com.unthinkable.training.entities.Employee, int)}.
	 */
	@Test
	void testUpdateEmployee() {
	    // Arrange
        int employeeId = 1;
        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setDepartments(new HashSet<>(Arrays.asList(new Department())));

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(employeeId);
        updatedEmployee.setDepartments(new HashSet<>(Arrays.asList(new Department())));

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(departmentRepository.findById(anyInt())).thenReturn(Optional.of(new Department()));
        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(new Department());
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        // Act
        Employee result = employeeService.updateEmployee(updatedEmployee, employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals(updatedEmployee.getDepartments(), result.getDepartments());
	}

	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#deleteEmployee(int)}
	 * when employee exists.
	 */
	@Test
	void testDeleteEmployee_WhenEmployeeExists() {
	     // Arrange
        int employeeId = 1;
        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        // Act
        boolean result = employeeService.deleteEmployee(employeeId);

        // Assert
        assertTrue(result);
        Mockito.verify(employeeRepository, times(1)).delete(existingEmployee);
	}

	/**
	 * Test method for {@link com.unthinkable.training.services.EmployeeService#deleteEmployee(int)}
	 * when employee not exists.
	 */
	@Test
	void testDeleteEmployee_WhenEmployeeNotExists() {
	     // Arrange
        int employeeId = 1;


        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act
        boolean result = employeeService.deleteEmployee(employeeId);

        // Assert
        assertFalse(result);
	}

}
