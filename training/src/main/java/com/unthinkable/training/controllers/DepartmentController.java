package com.unthinkable.training.controllers;

import com.unthinkable.training.entities.Department;
import com.unthinkable.training.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departmentList = this.departmentService.getAllDepartment();
        if(departmentList.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(departmentList));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("name") String name){
        Department d = this.departmentService.getByName(name);
        if(d == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(d);
    }




    @PostMapping
    public ResponseEntity<List<Department>> addDepartment(@RequestBody List<Department> departments){
        List<Department> d = null;
        try{
            d = (List<Department>) this.departmentService.addDepartment(departments);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).build();
        }
        return ResponseEntity.of(Optional.ofNullable(d));
    }

    @PutMapping("/{id}")
    public void updateDepartment(@RequestBody Department department, @PathVariable("id") int id){
        this.departmentService.updateDepartment(department,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") int id){
        if(!this.departmentService.deleteDepartment(id)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


}
