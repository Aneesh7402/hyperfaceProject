package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.DTO.ProjectDTO;
import com.project.hyperface_project.model.*;
import com.project.hyperface_project.repo.ProjectRepo;
import com.project.hyperface_project.DTO.ProjectInsert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service

public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;


    @Autowired
    private DepartmentService departmentService;

    @Autowired

    private EntityManager entityManager;

    public void deleteProject(Integer id){
        projectRepo.deleteById(id.longValue());
    }
    public Optional<Project> getProjectById(Integer id){
        return projectRepo.findById(id.longValue());
    }
    public List<Project> getProjectsByIds(List<Integer> listId){
        List<Project> projectList=new ArrayList<>();
        for(Integer id:listId){
            Optional<Project> proj=getProjectById(id);
            if(proj.isPresent()){
                projectList.add(proj.get());
            }
        }
        return projectList;
    }

    public List<Project> getAllProjects(){
        return projectRepo.findAll();
    }

    public void saveProject(Project project){
        projectRepo.save(project);
    }
    public String saveMyProject(ProjectInsert projectInsert){
        try{
            Project project=new Project(projectInsert.getName(),null,departmentService.getDeptById(projectInsert.getDeptId()).get());
            saveProject(project);
            return "Successful";
        }
        catch (NoSuchElementException e){
            return "Invalid dept id";
        }
    }
    public List<ProjectDTO> getProjs(){
        List<Project> projs=getAllProjects();
        List<ProjectDTO> projectDTOS=new ArrayList<>();
        Integer deptId;

        for(Project proj:projs){
            List<Integer> empList=new ArrayList<>();
            for(Employee emp:proj.getEmployeeList()){
                empList.add(proj.getProjectId());

            }
            if(proj.getDept()==null){
                deptId=null;
            }else{
                deptId=proj.getDept().getDepartmentId();
            }
            projectDTOS.add(new ProjectDTO(proj.getProjectId(),proj.getName(),deptId,empList));
        }
        return projectDTOS;
    }

    public String updateName(Integer id,String name){
        Optional<Project> proj=getProjectById(id);
        if(proj.isEmpty()){
            return "Unsuccessful";

        }
        else{
            Project project=proj.get();
            project.setName(name);
            saveProject(project);
            return "Successful";
        }

    }
}
