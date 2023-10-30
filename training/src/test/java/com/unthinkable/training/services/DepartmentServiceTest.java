package com.unthinkable.training.services;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.unthinkable.training.dao.DepartmentRepository;
import com.unthinkable.training.entities.Department;
import com.unthinkable.training.entities.Employee;


public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;
    


	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}


    @Test
    public void testGetAllDepartment() {
        // Arrange
    	
    	List<Department> expectedDepartmentList=new ArrayList<>();
    	Department department1 =new Department();
    	department1.setCreatedDate(LocalDateTime.now());
    	department1.setId(1);
    	department1.setLastModifiedDate(LocalDateTime.now());
    	department1.setName("IT");
    	Set<Employee> employeeList=new HashSet<>();
    	Employee emp1=new Employee();
    	Employee emp2=new Employee();
    	employeeList.add(emp1);
    	employeeList.add(emp2);
    	department1.setEmployees(employeeList);
    	expectedDepartmentList.add(department1);
    	
    	Mockito.when(departmentRepository.findAll()).thenReturn(expectedDepartmentList);

        // Act
        List<Department> actualDepartmentList = departmentService.getAllDepartment();

        // Assert

        assertEquals(expectedDepartmentList.size(), actualDepartmentList.size());
        assertArrayEquals(expectedDepartmentList.toArray(), actualDepartmentList.toArray());
    }

    @Test
    public void testGetByName_WhenDepartmentExists() {
        // Arrange
        String departmentName = "TestDepartment";
        Department expectedDepartment = new Department();
        expectedDepartment.setName(departmentName);
        Mockito.when(departmentRepository.findByName(departmentName)).thenReturn(Optional.of(expectedDepartment));

        // Act
        Department actualDepartment = departmentService.getByName(departmentName);

        // Assert
        assertEquals(expectedDepartment, actualDepartment);
    }

    @Test
    public void testGetByName_WhenDepartmentNotExists() {
        // Arrange
        String departmentName = "TestDepartment";

        Mockito.when(departmentRepository.findByName(departmentName)).thenReturn(Optional.empty());

        // Act
        Department actualDepartment = departmentService.getByName(departmentName);

        // Assert
        assertNull(actualDepartment);
    }
    @Test
    public void testAddDepartment() {
        // Arrange
        List<Department> expectedDepartments = Arrays.asList(new Department(), new Department());
        Mockito.when(departmentRepository.saveAll(expectedDepartments)).thenReturn(expectedDepartments);

        // Act
        List<Department> actualDepartments = departmentService.addDepartment(expectedDepartments);

        // Assert
        assertEquals(expectedDepartments.size(), actualDepartments.size());
        assertArrayEquals(expectedDepartments.toArray(), actualDepartments.toArray());
    }

    @Test
    public void testUpdateDepartment() {
        // Arrange
        Department department = new Department();
        int id = 1;

        // Act
        departmentService.updateDepartment(department, id);

        // Assert
        Mockito.verify(departmentRepository, Mockito.times(1)).save(department);
        assertEquals(id, department.getId());
    }

    @Test
    public void testDeleteDepartment_WhenOnlyDepartment() {
        // Arrange
        int departmentId = 1;
        Department department = new Department();
        department.setId(departmentId);
        Set<Employee> employees=new HashSet<>();
        Employee emp=new Employee();
        Set<Department> departments =new HashSet<>();
        departments.add(department);
        emp.setDepartments(departments);
        employees.add(emp);
        department.setEmployees(employees);
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        
        // Act
        boolean result = departmentService.deleteDepartment(departmentId);

        // Assert
        assertFalse(result);
    }
    
    @Test
    public void testDeleteDepartment_WhenNoDepartment() {
        // Arrange
        int departmentId = 1;

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        
        // Act
        boolean result = departmentService.deleteDepartment(departmentId);

        // Assert
        assertFalse(result);
    }
    
    @Test
    public void testDeleteDepartment_WhenDepartmentDeleted() {
        // Arrange
        int departmentId = 1;
        Department department = new Department();
        department.setId(departmentId);
        Set<Employee> employees=new HashSet<>();
        Employee emp=new Employee();
        Set<Department> departments =new HashSet<>();
        Department newDepartment=new Department();
        newDepartment.setId(2);
        departments.add(department);
        departments.add(newDepartment);
        emp.setDepartments(departments);
        employees.add(emp);
        department.setEmployees(employees);

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        
        // Act
        boolean result = departmentService.deleteDepartment(departmentId);

        // Assert
        assertTrue(result);
        Mockito.verify(departmentRepository, Mockito.times(1)).delete(department);

    }
}


