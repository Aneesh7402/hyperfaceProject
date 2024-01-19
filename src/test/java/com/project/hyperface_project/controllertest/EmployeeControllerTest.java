package com.project.hyperface_project.controllertest;


import com.project.hyperface_project.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.BeanMapping;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
@WebMvcTest(controllers = EmployeeControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;
//    @BeforeEach
//    public void init(){
//        Department department=Department.builder().departmentId(1).name("iashd").employeeList(null).projectList(null).build();
//        List<Project> projectList= Stream.of(Project.builder().projectId(1).employeeList(null).build(),)
//    }
    @Test
    public void dummyTester(){
        assertTrue(true);
    }



    @Test
    public void EmployeeController_CreateEmployee_Created(){

    }


}
