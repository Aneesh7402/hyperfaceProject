package com.project.hyperface_project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "department")

@NoArgsConstructor
@Builder
@AllArgsConstructor

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    @Column
    private String name;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
    @JsonManagedReference
    @OneToMany(mappedBy = "department",cascade = CascadeType.PERSIST)
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "dept",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Project> projectList;

    public Department(String name, List<Employee> employeeList, List<Project> projectList) {
        this.name = name;
        this.employeeList = employeeList;
        this.projectList = projectList;
    }
}
