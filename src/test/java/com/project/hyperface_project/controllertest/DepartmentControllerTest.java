package com.project.hyperface_project.controllertest;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.hyperface_project.DTO.DeptDTO;
import com.project.hyperface_project.controller.DeptController;
import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.repotest.DepartmentRepoTest;
import com.project.hyperface_project.service.DepartmentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;
import java.awt.*;

@WebMvcTest(controllers = DeptController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)

public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private ObjectMapper objectMapper;

    private Department department;
//    private DeptDTO deptDTO;

    @BeforeEach
    public void init(){
        Department department=Department.builder().name("asd").departmentId(1).projectList(null).employeeList(null).build();

    }
    @Test
    public void DepartmentController_CreateDepartment_Created() throws Exception{
        DeptDTO deptDTO=new DeptDTO(1,"asd",null,null);
        ResponseEntity<DeptDTO> deptDTOResponseEntity=new ResponseEntity<>(deptDTO, HttpStatus.CREATED);
        //given(departmentService.saveMyDept(Mockito.any())).willAnswer((Answer<?>) deptDTOResponseEntity);
        when(departmentService.saveMyDept(Mockito.any())).thenReturn(deptDTOResponseEntity);
        System.out.println(deptDTO.getDeptName());
        ResultActions response=mockMvc.perform(post("/dept/insertDepartment").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(deptDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
