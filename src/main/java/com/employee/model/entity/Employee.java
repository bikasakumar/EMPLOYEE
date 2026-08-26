package com.employee.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private Double companyName;

    
}