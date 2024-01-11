package com.project.hyperface_project.controller;


import com.project.hyperface_project.DTO.DeptInsert;
import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.model.Project;
import com.project.hyperface_project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    DepartmentService departmentService;
    @GetMapping("allDepts")
    public ResponseEntity<List<Department>> getDepts(){

        return new ResponseEntity<>(departmentService.getAllDepts(), HttpStatus.FOUND);
    }
    @GetMapping("deptById/{id}")
    public ResponseEntity<Department> getDeptById(@PathVariable("id") Integer id){
        return departmentService.getDeptById(id).map(object ->
                        new ResponseEntity<>(object, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("allDeptProjects/{id}")
    public Integer getDeptProjects(@PathVariable("id") Integer id){
        List<Project> projs= departmentService.getDeptProjects(id);
        return projs.get(0).getProjectId();
    }
    @PostMapping("insertDepartment")
    public String saveDepartment(@RequestBody DeptInsert deptInsert){
        return departmentService.saveMyDept(deptInsert);

    }
    @GetMapping("/fireDept/{id}")
    public String fireDept(@PathVariable("id") Integer id){
        return departmentService.fireDept(id);
    }

}
