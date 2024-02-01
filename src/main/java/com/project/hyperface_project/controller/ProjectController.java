package com.project.hyperface_project.controller;

import com.project.hyperface_project.DTO.ProjectDTO;
import com.project.hyperface_project.DTO.ProjectInsert;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proj")

public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/getAllProjs")
    public ResponseEntity<List<ProjectDTO>> getProjs(){
        return projectService.getProjs();

    }
    @GetMapping("/fireProj/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String fireProj(@PathVariable("id") Integer id){
        projectService.deleteProject(id);
        return "Success";
    }
    @PostMapping("/saveProject")
    public ResponseEntity<ProjectDTO> saveProj(@RequestBody ProjectInsert projectInsert) throws RuntimeException{
        System.out.println("saveProj");
        return projectService.saveMyProject(projectInsert);
    }
    @PostMapping("/update")
    public ResponseEntity<ProjectDTO> updation(@RequestBody ProjectDTO projectDTO) throws InvalidFieldException {
        return projectService.update(projectDTO);
    }

//    @PostMapping("/assignEmpToProject")
//    public String assEmpProj(@RequestBody )
}
