package com.unthinkable.training.services;

import com.unthinkable.training.dao.DepartmentRepository;
import com.unthinkable.training.entities.Department;
import com.unthinkable.training.entities.Employee;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartment(){
        return (List<Department>)this.departmentRepository.findAll();
    }

    public Department getByName(String Name){
        Optional<Department> department = Optional.empty();
        try{
            department = this.departmentRepository.findByName(Name);
        }catch (Exception e){
            e.printStackTrace();
        }

        return department.orElse(null);
    }

    public List<Department> addDepartment(List<Department> department){
        return this.departmentRepository.saveAll(department);
    }

    public void updateDepartment(@NotNull Department department, int id){
        department.setId(id);
        this.departmentRepository.save(department);
    }

    public boolean deleteDepartment(int id) {
        Optional<Department> departmentOpt = this.departmentRepository.findById(id);

        if (departmentOpt.isEmpty()) {
            return false;
        }

        Department department = departmentOpt.get();

        for (Employee e : department.getEmployees()) {
            if (e.getDepartments().size() == 1) {
                return false;
            } else {
                e.getDepartments().remove(department);
            }
        }

        department.getEmployees().clear();
        this.departmentRepository.delete(department);

        return true;
    }
}
