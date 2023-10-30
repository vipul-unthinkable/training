package com.unthinkable.training.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department  extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "departments")
    @JsonIgnoreProperties("departments")
    private Set<Employee> employees;

}

//package com.unthinkable.training.entities;
//
//import java.util.Set;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Entity
//@Getter
//@Setter
//public class Department extends Auditable  {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String name;
//
//    @ManyToMany(mappedBy = "departments")
//    @JsonIgnoreProperties("departments")
//    private Set<Employee> employees;
//
//
//}