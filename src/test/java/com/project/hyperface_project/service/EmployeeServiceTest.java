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

import static org.junit.jupiter.api.Assertions.*;
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

        employee=Employee.builder().empId(1).departmentId(department).projects(projectList).build();
        empDTO=EmpDTO.builder().empID(1).departmentID(2).projectIDs(Stream.of(1,2).collect(Collectors.toList())).name("blah").build();


    }

    @Test
    public void EmployeeService_saveMyEmp_ReflectsEverything() throws InvalidFieldException {
        //Arrange
        when(employeeRepo.save(any(Employee.class))).then(AdditionalAnswers.returnsFirstArg());
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
    public void EmployeeService_testUpdateValidEmployee_Created() throws InvalidFieldException {
        // Mock data

        Employee existingEmployee=employee;

        // Mock behaviors
        when(employeeRepo.save(any(Employee.class))).then(AdditionalAnswers.returnsFirstArg());
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

    @Test
    public void EmployeeService_testUpdateInvalidEmployee_InvalidID() {
        // Mock data
        EmpDTO empDTOLocal=EmpDTO.builder().name("assd").departmentID(1).projectIDs(Stream.of(1,2).toList()).build();


        // Mock behaviors
        when(employeeService.getEmpByID(empDTO.getEmpID())).thenReturn(Optional.empty());

        // AssertAndVerify
        assertThrows(InvalidFieldException.class, () -> employeeService.update(empDTO));


        verify(employeeMapper, never()).updateEmployeeFromDto(any(), any());
        verify(employeeRepo, never()).save(any());
    }

    @Test
    public void EmployeeService_testUpdate_InvalidDepartment_InvalidFieldException(){
        //Arrage
        EmpDTO empDTOLocal=EmpDTO.builder().empID(1).name("assd").departmentID(2).projectIDs(Stream.of(1,2).toList()).build();
        when(departmentService.getDeptById(empDTO.getDepartmentID())).thenReturn(Optional.empty());
        when(employeeService.getEmpByID(empDTO.getEmpID())).thenReturn(Optional.ofNullable(employee));
        //Act
        assertThrows(InvalidFieldException.class, () -> employeeService.update(empDTO));
        //Assert
        verify(employeeMapper, times(1)).updateEmployeeFromDto(any(), any());
        verify(employeeRepo, never()).save(any());
    }

    @Test
    public void testFireMyEmpSuccess() throws InvalidFieldException, RuntimeException {
        // Mock data
        Integer employeeId = 1;

        // Mock behavior - Assume fireEmployeeById succeeds without throwing exceptions
        doNothing().when(employeeRepo).deleteById(employeeId.longValue());

        // Invoke the method
        ResponseEntity<String> responseEntity = employeeService.fireMyEmp(employeeId);

        // Verify behavior
        verify(employeeRepo, times(1)).deleteById(employeeId.longValue());

        // Assert the response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Deletion Successful", responseEntity.getBody());
    }

    @Test
    public void testFireMyEmpException() throws InvalidFieldException {
        // Mock data
        Integer employeeId = 123;

        // Mock behavior - Assume fireEmployeeById throws an exception
        doThrow(new NullPointerException("Some exception")).when(employeeRepo).deleteById(employeeId.longValue());

        // Invoke the method and expect a RuntimeException
        assertThrows(RuntimeException.class, () -> employeeService.fireMyEmp(employeeId));

        // Verify behavior
        verify(employeeRepo, times(1)).deleteById(employeeId.longValue());
    }
}









