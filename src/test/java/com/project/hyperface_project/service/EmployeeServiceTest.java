package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.mapper.EmployeeMapper;
import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.model.Employee;
import com.project.hyperface_project.model.Project;
import com.project.hyperface_project.repo.EmployeeRepo;

import com.sun.source.tree.ModuleTree;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepo employeeRepo;
    @Mock
    private DepartmentService  departmentService;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private ProjectService projectService;
    @InjectMocks
    private EmployeeService employeeService;

    private static Department department;

    private  List<Project> projectList;

    private static Employee employee;
    private EmpDTO empDTO;

    @BeforeEach
    public void init(){
        department=Department.builder().name("oasjd").departmentId(1).employeeList(null).projectList(null).build();
        projectList=new ArrayList<>();
        projectList.add(Project.builder().name("blah").projectId(1).employeeList(null).dept(department).build());
        projectList.add(Project.builder().name("blah2").projectId(2).employeeList(null).dept(department).build());
        department.setProjectList(projectList);
//        when(projectService.getProjectsByIds(Mockito.anyList())).thenReturn(projectList);
        employee=Employee.builder().empId(1).departmentId(department).projects(projectList).build();
        empDTO=EmpDTO.builder().empID(1).departmentID(2).projectIDs(Stream.of(1,2).collect(Collectors.toList())).name("blah").build();
        when(employeeRepo.save(any(Employee.class))).then(AdditionalAnswers.returnsFirstArg());

    }

    @Test
    public void EmployeeService_saveMyEmp_ReflectsEverything() throws InvalidFieldException {
        //Arrange

        Optional<Department> departmentOptional=Optional.ofNullable(department);
        when(departmentService.getDeptById(any(Integer.class))).thenReturn(departmentOptional);




        List<Integer> projectIDs= Arrays.asList(1,2);
        EmpDTO empDTOLocal=EmpDTO.builder().name("assd").departmentID(1).projectIDs(projectIDs).build();

        //Act
        ResponseEntity<EmpDTO> empDTOResponseEntity= employeeService.saveMyEmp(empDTOLocal);
        //Assert
        verify(employeeRepo).save(any(Employee.class));
        Assertions.assertThat(empDTOResponseEntity.getBody().getDepartmentID()).isEqualTo(department.getDepartmentId());
    }

    @Test
    public void testUpdateValidEmployee_C() throws InvalidFieldException {
        // Mock data

        Employee existingEmployee=employee;

        // Mock behaviors
        when(employeeService.getEmpByID(empDTO.getEmpID())).thenReturn(Optional.of(existingEmployee));
        when(departmentService.getDeptById(empDTO.getDepartmentID())).thenReturn(Optional.of(department));
        when(projectService.getProjectsByIds(Mockito.anyList())).thenReturn(projectList);

        // Invoke the method
        ResponseEntity<EmpDTO> responseEntity = employeeService.update(empDTO);

        // Verify behaviors
        verify(employeeMapper).updateEmployeeFromDto(eq(empDTO), eq(existingEmployee));
        verify(employeeRepo).save(existingEmployee);

        // Assert response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }




}



