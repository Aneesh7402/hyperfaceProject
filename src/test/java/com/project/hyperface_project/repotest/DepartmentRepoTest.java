package com.project.hyperface_project.repotest;

import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.repo.DepartmentRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    @Test
    public void DepartmentRepo_findAll_ReturnsEverything(){
        //Arrage
        Department department=new Department("blah",null,null);
        Department department1=new Department("blah2",null,null);
        departmentRepo.save(department1);
        departmentRepo.save(department);
        //Act
        List<Department> departments=departmentRepo.findAllById(Stream.of(1,2).map(Integer::longValue).collect(Collectors.toList()));

        //Assert
        Assertions.assertThat(departments.get(1).getName()).isEqualTo(department.getName());

    }
}
