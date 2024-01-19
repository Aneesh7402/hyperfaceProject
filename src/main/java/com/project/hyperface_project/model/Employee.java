package com.project.hyperface_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.hyperface_project.model.*;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "employee")

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    @Column
    private String name;

    @PreRemove
    public void remove(){
        if(!projects.isEmpty()){
            for(Project e:projects){
                e.getEmployeeList().remove(this);
            }}
    }
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="departmentId")
    private Department departmentId;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    @JsonManagedReference
    @ManyToMany(targetEntity = Project.class)
    @JoinTable(name="emp_project",joinColumns = @JoinColumn(name="empId"),inverseJoinColumns = @JoinColumn(name = "projectId"))
    private List<Project> projects;

    public Employee(String name, Department departmentId, List<Project> projects) {
        this.name = name;
        this.departmentId = departmentId;
        this.projects = projects;
    }
}
