package com.project.hyperface_project.controller;

import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.hyperface_project.service.*;
import com.project.hyperface_project.DTO.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/emp")
public class EmpController {



    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private  final ProjectService projectService;

    @Autowired
    public EmpController(EmployeeService employeeService, DepartmentService departmentService, ProjectService projectService){
        this.employeeService=employeeService;
        this.departmentService=departmentService;
        this.projectService=projectService;
    }
    @GetMapping("/allEmployees")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployees();
    }



    @PostMapping("/insertProject")
    public  ResponseEntity<ProjectDTO> saveProject(@RequestBody ProjectInsert projectInsert) throws RuntimeException{
        return projectService.saveMyProject(projectInsert);
    }
    @PostMapping("insertEmployee")
    public ResponseEntity<EmpDTO> saveEmployee(@RequestBody EmpDTO employeeInsert) throws InvalidFieldException,NullPointerException {
        return employeeService.saveMyEmp(employeeInsert);
    }
    @GetMapping("fireEmployee/{id}")
    public ResponseEntity<String> fireEmployee(@PathVariable("id") Integer id) throws RuntimeException,InvalidFieldException{
        return employeeService.fireMyEmp(id);
    }

    @PostMapping("/updateEmployee")
    public ResponseEntity<EmpDTO> updateEmp(@RequestBody EmpDTO empDTO) throws InvalidFieldException{
        return employeeService.update(empDTO);
    }

    @GetMapping("/getAllEmps")
    public List<EmpDTO> getAllEmps(){
        return employeeService.getAllEmps();

    }

    @GetMapping("/fireEmp/{id}")
    public String fireEmp(@PathVariable("id") Integer id){
        employeeService.fireEmployeeById(id);
        return "done";
    }



}
