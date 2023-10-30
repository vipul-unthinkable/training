package com.unthinkable.training.dao;

import com.unthinkable.training.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByName(String name);

    void deleteById(int id);

}
