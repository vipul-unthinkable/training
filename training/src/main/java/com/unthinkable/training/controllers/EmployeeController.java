package com.unthinkable.training.controllers;

import com.unthinkable.training.entities.Employee;
import com.unthinkable.training.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> e = this.employeeService.getAllEmployees();
        if(this.employeeService.getAllEmployees().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.ofNullable(e));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id){
        Employee e = this.employeeService.getEmployeeById(id);
        if(e == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(e));
    }

    @PostMapping
    public ResponseEntity<List<Employee>> addEmployees(@RequestBody List<Employee> employeeList){
        List<Employee> employees;
        try{
            employees = this.employeeService.addEmployees(employeeList);
            if(employees == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).build();
        }
        return ResponseEntity.of(Optional.ofNullable(employees));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee,@PathVariable("id") int id){
        Employee e = this.employeeService.updateEmployee(employee,id);
        if(e == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id")int id){
        this.employeeService.deleteEmployee(id);
    }
}
