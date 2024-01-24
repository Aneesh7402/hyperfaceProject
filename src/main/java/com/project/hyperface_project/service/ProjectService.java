package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.DTO.ProjectDTO;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.mapper.ProjectMapper;
import com.project.hyperface_project.model.*;
import com.project.hyperface_project.repo.EmployeeRepo;
import com.project.hyperface_project.repo.ProjectRepo;
import com.project.hyperface_project.DTO.ProjectInsert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.interfaces.EdECKey;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private EmployeeRepo employeeRepo;


    @Autowired
    private DepartmentService departmentService;

    @Autowired

    private EntityManager entityManager;

    @Autowired
    private ProjectMapper projectMapper;

    //Utility functions

    public List<Employee> getALlEmpById(List<Integer> empIds){
        return employeeRepo.findAllById(empIds.stream().map(Integer::longValue).collect(Collectors.toList()));
    }
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
    public ProjectDTO projectToDTO(Project project){
        List<Integer> empIDs=new ArrayList<>();
        if(project.getEmployeeList()!=null){
        for(Employee employee:project.getEmployeeList()){
            empIDs.add(employee.getEmpId());
        }}
        return new ProjectDTO(project.getProjectId(),project.getName(),project.getDept().getDepartmentId(),empIDs);
    }

    public List<Project> getAllProjects(){
        return projectRepo.findAll();
    }

    //Controller functions

    public Project saveProject(Project project){
        return projectRepo.save(project);
    }
    public ResponseEntity<ProjectDTO> saveMyProject(ProjectInsert projectInsert) throws RuntimeException{
        try{

            Project project=new Project(projectInsert.getName(),null,departmentService.getDeptById(projectInsert.getDeptId()).get());
            return new ResponseEntity<>(projectToDTO(saveProject(project)),HttpStatus.CREATED);
        }
        catch (NoSuchElementException e){
            throw new RuntimeException("Invalid dept Id",e);
        }
    }



    //Controller calls
    public ResponseEntity<List<ProjectDTO>> getProjs(){
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
        return new ResponseEntity<>(projectDTOS, HttpStatus.FOUND);
    }

    public ResponseEntity<ProjectDTO> update(ProjectDTO projectDTO) throws InvalidFieldException{
        Optional<Project> proj=getProjectById(projectDTO.getProjectId());
        if(proj.isEmpty()){
            throw new InvalidFieldException("Invalid project id");
        }
        else{
            Project project=proj.get();
            projectMapper.updateProjectFromDTO(projectDTO,project);
            if(!(project.getDept().getDepartmentId().equals(projectDTO.getDeptId()))){
                throw new InvalidFieldException("You cannot change a project's department");
            }
            if(projectDTO.getEmployeeIds()!=null){
                List<Employee> employeeUpdation=getALlEmpById(projectDTO.getEmployeeIds());
                List<Employee> employeePrev=project.getEmployeeList();
                List<Employee> employeeAddList=new ArrayList<>(employeeUpdation);
                employeeAddList.removeAll(employeePrev);
                employeePrev.removeAll(employeeUpdation);
                for(Employee employee:employeeAddList){
                    employee.getProjects().add(project);
                    employeeRepo.save(employee);
                }
                for(Employee employee:employeePrev){
                    employee.getProjects().remove(project);
                    employeeRepo.save(employee);
                }
            }
            return new ResponseEntity<>(projectToDTO(saveProject(project)) ,HttpStatus.OK);
        }

    }
}
