package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.DTO.idk;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.repo.EmployeeRepo;
import com.project.hyperface_project.DTO.EmployeeInsert;
import com.project.hyperface_project.DTO.UnPw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.project.hyperface_project.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final DepartmentService departmentService;
    private final  ProjectService projectService;
    
    private final Environment environment;
    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo,DepartmentService departmentService,ProjectService projectService,Environment environment){

        this.employeeRepo=employeeRepo;
        this.departmentService=departmentService;
        this.projectService=projectService;
        this.environment=environment;
    }
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    public void saveEmployee(Employee employee){
        employeeRepo.save(employee);
    }
    public void fireEmployeeById(Integer id){
        employeeRepo.deleteById(id.longValue());
    }

    public String saveMyEmp(EmployeeInsert employeeInsert){
        Optional<Department> department=departmentService.getDeptById(employeeInsert.getDeptId());
        if(department.isEmpty()){
            return "Wrong Department";
        }
        else{
            List<Project> projectList=projectService.getProjectsByIds(employeeInsert.getProjectIds());
            for(Project project:projectList){
                if(project.getDept()!=department.get()){
                    throw new InvalidFieldException("Wrong Department");
                }
            }
            Employee employee=new Employee(employeeInsert.getName(),department.get(),projectList);
            saveEmployee(employee);
            return "Successful";
        }
    }
    public String fireMyEmp(Integer id, UnPw unpw){
        if(!(unpw.getPassword().equals(environment.getProperty("my.password")) & unpw.getUsername().equals(environment.getProperty("my.username")))){
            return "Invalid credentials";
        }
        else{
            fireEmployeeById(id);
            return "Successful";
    }
}
    public List<EmpDTO> getAllEmps(){
        List<Employee> employeeList= getAllEmployees();
        List<EmpDTO> empDTOS=new ArrayList<>();
        Integer deptId;

        for(Employee emp:employeeList){
            List<idk> projectList=new ArrayList<>();
            for(Project proj:emp.getProjects()){
                projectList.add(new idk(proj.getProjectId(),proj.getName()));

            }
            if(emp.getDepartmentId()==null){
                deptId=null;
            }else{
                deptId=emp.getDepartmentId().getDepartmentId();
            }
            empDTOS.add(new EmpDTO(emp.getEmpId(),emp.getName(),deptId,projectList));
        }
        return empDTOS;
    }
}
