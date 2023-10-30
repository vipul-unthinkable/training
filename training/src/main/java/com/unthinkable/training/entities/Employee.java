package com.unthinkable.training.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Employee extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private int experience;
    private String city;
    private String state;
    private String mobileNumber;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("employees")
    private Set<Department> departments;



}
//package com.unthinkable.training.entities;
//
//import com.fasterxml.jackson.annotation.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//import java.util.Set;
//@Entity
//@Getter
//@Setter
//public class Employee extends Auditable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String name;
//    private int experience;
//    private String city;
//    private String state;
//    private String mobileNumber;
//
//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JsonIgnoreProperties("employees")
//    private Set<Department> departments;
//
//
//}