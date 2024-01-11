package com.project.hyperface_project.controller;

import com.project.hyperface_project.DTO.ProjectDTO;
import com.project.hyperface_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proj")

public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/getAllProjs")
    public List<ProjectDTO> getProjs(){
        return projectService.getProjs();

    }
    @GetMapping("/fireProj/{id}")
    public String fireProj(@PathVariable("id") Integer id){
        projectService.deleteProject(id);
        return "Success";
    }
    @GetMapping("/name/{id}/{name}")
    public String nameChange(@PathVariable("name") String name,@PathVariable("id") Integer id){
        return projectService.updateName(id,name);
    }

//    @PostMapping("/assignEmpToProject")
//    public String assEmpProj(@RequestBody )
}
