package com.unthinkable.training.services;

import com.unthinkable.training.dao.DepartmentRepository;
import com.unthinkable.training.dao.EmployeeRepository;
import com.unthinkable.training.entities.Department;
import com.unthinkable.training.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return (List<Employee>) this.employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id){
        Optional<Employee> employee = Optional.empty();
        try {
            employee = this.employeeRepository.findById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return employee.orElse(null);
    }

    public List<Employee> addEmployees(List<Employee> employeeList){
        for(Employee e:employeeList){
            System.out.println(e);
            if(e.getDepartments().isEmpty()) {
                return null;
            }
            Set<Department> managedDepartments = new HashSet<>();
            for(Department department : e.getDepartments()){
                Department managedDepartment;
                if(department != null && department.getId() > 0){
                    Optional<Department> departmentOpt = departmentRepository.findById(department.getId());
                    if(departmentOpt.isPresent()){
                        managedDepartment = departmentOpt.get();
                    } else {
                        managedDepartment = departmentRepository.save(department);
                    }
                } else {
                    assert department != null;
                    managedDepartment = departmentRepository.save(department);
                }
                managedDepartments.add(managedDepartment);
            }
            e.setDepartments(managedDepartments);
        }
        return this.employeeRepository.saveAll(employeeList);
    }

    public Employee updateEmployee(Employee employee,int id){
        try {
            Employee employee1 = employeeRepository.findById(employee.getId()).get();
            employee.setId(id);
            Set<Department> departmentToAdd = new HashSet<>();
            for (Department d : employee.getDepartments()) {
                Optional<Department> check = this.departmentRepository.findById(d.getId());
                if (check.isPresent()) {
                    departmentToAdd.add(check.get());
                } else {
                    departmentToAdd.add(departmentRepository.save(d));
                }
            }
            employee.getDepartments().clear();
            departmentToAdd.addAll(employee1.getDepartments());
            employee.setDepartments(departmentToAdd);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return this.employeeRepository.save(employee);
    }

    public boolean deleteEmployee(int id){
        Optional<Employee> e = this.employeeRepository.findById(id);
        boolean result = true;
        if(e.isEmpty()){
            result = false;
        }else{
            this.employeeRepository.delete(e.get());
        }
        return result;

    }

	public void setDepartmentRepository(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
    
    
}
