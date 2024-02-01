package com.project.hyperface_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter

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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="department")
    private Department departmentId;



    @JsonManagedReference
    @ManyToMany(targetEntity = Project.class)
    @JoinTable(name="emp_project",joinColumns = @JoinColumn(name="empId"),inverseJoinColumns = @JoinColumn(name = "projectId"))
    private List<Project> projects;

    @JsonBackReference
    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private UserAuth userAuth;

    public Employee(String name, Department departmentId, List<Project> projects) {
        this.name = name;
        this.departmentId = departmentId;
        this.projects = projects;
    }
}
