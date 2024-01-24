package com.project.hyperface_project.repotest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.model.Employee;
import com.project.hyperface_project.model.Project;
import com.project.hyperface_project.repo.DepartmentRepo;
import com.project.hyperface_project.repo.EmployeeRepo;
import com.project.hyperface_project.repo.ProjectRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class DepartmentRepoTest {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ProjectRepo projectRepo;

    private  Employee employee1;

    private Employee employee2;
    private List<Project> projects;
    private Department department;

    @BeforeEach
    public void init(){
        department=new Department("blah",null,null);
        projects= Stream.of(Project.builder().build(),Project.builder().name("project2").build()).toList();
        employee1=Employee.builder().build();
        employee2=Employee.builder().build();
    }

    @Test
    public void DepartmentRepo_findAll_ReturnsEverything(){
        //Arrage

        Department department1=new Department("blah2",null,null);
        departmentRepo.save(department1);
        departmentRepo.save(department);
        //Act
        List<Department> departments=departmentRepo.findAllById(Stream.of(1,2).map(Integer::longValue).collect(Collectors.toList()));

        //Assert
        Assertions.assertThat(departments.get(1).getName()).isEqualTo(department.getName());

    }


}
