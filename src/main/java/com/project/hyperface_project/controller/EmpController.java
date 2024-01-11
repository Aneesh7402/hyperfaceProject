package com.project.hyperface_project.controller;

import com.project.hyperface_project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("allEmployees")
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployees();
    }



    @PostMapping("insertProject")
    public  String saveProject(@RequestBody ProjectInsert projectInsert){
        return projectService.saveMyProject(projectInsert);
    }
    @PostMapping("insertEmployee")
    public String saveEmployee(@RequestBody EmployeeInsert employeeInsert){
        return employeeService.saveMyEmp(employeeInsert);
    }
    @PostMapping("fireEmployee/{id}")
    public String fireEmployee(@PathVariable("id") Integer id,@RequestBody UnPw unpw){
        return employeeService.fireMyEmp(id,unpw);
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
