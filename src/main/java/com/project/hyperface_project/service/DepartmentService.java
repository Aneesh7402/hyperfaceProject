package com.project.hyperface_project.service;

import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.model.Employee;
import com.project.hyperface_project.model.Project;
import com.project.hyperface_project.repo.DepartmentRepo;
import com.project.hyperface_project.repo.EmployeeRepo;
import com.project.hyperface_project.repo.ProjectRepo;
import com.project.hyperface_project.DTO.DeptInsert;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;


    @Autowired

    private ProjectRepo projectRepo;

    @Autowired
    private Environment environment;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepo employeeRepo;


//    @Autowired
//    public DepartmentService(DepartmentRepo departmentRepo, Environment environment,ProjectRepo projectRepo,EntityManager entityManager,EmployeeRepo employeeRepo){
//        this.departmentRepo=departmentRepo;
//        this.environment=environment;
//        this.projectRepo=projectRepo;
//        this.entityManager=entityManager;
//        this.employeeRepo=employeeRepo;
//
//    }
    public List<Department> getAllDepts(){
        return departmentRepo.findAll();
    }
    public void saveDept(Department department){
        departmentRepo.save(department);
    }

    public Optional<Department> getDeptById(Integer id){
        return departmentRepo.findById(id.longValue());

    }
    public void deleteDeptbyId(Integer id){
        departmentRepo.deleteById(id.longValue());
    }
    public String saveMyDept(DeptInsert deptInsert){
        if(!(deptInsert.getPassword().equals(environment.getProperty("my.password")) & deptInsert.getUsername().equals(environment.getProperty("my.username")))){
            return "Invalid credentials";
        }
        else{
            Department department=new Department(deptInsert.getDeptName(),null,null);
            saveDept(department);
            return "Successful";}
    }
    public List<Project> getDeptProjects(Integer id){
        Optional<Department> maybeDept=getDeptById(id);
        if(maybeDept.isEmpty()){
            return null;
        }
        else{
            Department dept=maybeDept.get();
            return dept.getProjectList();
        }
    }
    public String fireDept(Integer id){
        Department department=getDeptById(id).get();
        for(Project proj:department.getProjectList()){
            deleteProject(proj.getProjectId());
        }
        for(Employee emp:department.getEmployeeList()){
            emp.setDepartmentId(null);
            employeeRepo.save(emp);
        }

        department.setEmployeeList(null);
        departmentRepo.save(department);
        deleteDeptbyId(id);
        return "Successful";
    }
    public void deleteProject(Integer id) {
        System.out.println(id);
        projectRepo.deleteById(id.longValue());
    }
}
