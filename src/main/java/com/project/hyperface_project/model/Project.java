package com.project.hyperface_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@NoArgsConstructor


public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;



    @Column
    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy ="projects",targetEntity = Employee.class)
    private List<Employee> employeeList;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department dept;

    @PreRemove
    public void  remove(){
        if(!employeeList.isEmpty()){
        for(Employee e:employeeList){
            e.getProjects().remove(this);
        }}
    }


    public Project(String name, List<Employee> employeeList, Department dept) {
        this.name = name;
        this.employeeList = employeeList;
        this.dept = dept;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
