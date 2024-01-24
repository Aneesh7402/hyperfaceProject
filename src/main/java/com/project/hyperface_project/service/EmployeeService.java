package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.*;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.mapper.EmployeeMapper;
import com.project.hyperface_project.repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.project.hyperface_project.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;


    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private  ProjectService projectService;
    @Autowired
    private Environment environment;

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    public Employee saveEmployee(Employee employee){
        return employeeRepo.save(employee);
    }
    public void fireEmployeeById(Integer id){
         employeeRepo.deleteById(id.longValue());
    }

    public Optional<Employee> getEmpByID(Integer id){return employeeRepo.findById(id.longValue());}

    public EmpDTO empToDTO(Employee employee){
        List<Integer> projects=new ArrayList<>();
        Integer deptId=null;
        if(employee.getDepartmentId()!=null){
            deptId=employee.getDepartmentId().getDepartmentId();
        }
        if(employee.getProjects()!=null){
            projects=employee.getProjects().stream().map(Project::getProjectId).toList();
        }
        return  EmpDTO.builder().name(employee.getName()).departmentID(deptId).projectIDs(projects).empID(employee.getEmpId()).build();

    }

    //Controller calls

    public List<EmpDTO> getAllEmps(){
        List<Employee> employeeList= getAllEmployees();
        List<EmpDTO> empDTOS=new ArrayList<>();
        Integer deptId;

        for(Employee emp:employeeList){
            List<Integer> projectList=new ArrayList<>();
            for(Project proj:emp.getProjects()){
                projectList.add(proj.getProjectId());

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

    public ResponseEntity<EmpDTO> saveMyEmp(EmpDTO employeeInsert) throws InvalidFieldException,NullPointerException {
        Optional<Department> department=departmentService.getDeptById(employeeInsert.getDepartmentID());
        if(department.isEmpty()){

            throw new InvalidFieldException("Wrong Department");
        }
        else{
            List<Project> projectList=projectService.getProjectsByIds(employeeInsert.getProjectIDs());
            for(Project project:projectList){
                if(project.getDept()!=department.get()){

                    throw new InvalidFieldException("Wrong Department");
                }
            }

            Employee employee=new Employee(employeeInsert.getName(),department.get(),projectList);
            Employee employee1=saveEmployee(employee);
            return new ResponseEntity<>(new EmpDTO(employee1.getEmpId(),employee1.getName(),employee1.getDepartmentId().getDepartmentId(),employeeInsert.getProjectIDs()), HttpStatus.CREATED);


        }
    }
    public ResponseEntity<String> fireMyEmp(Integer id) throws InvalidFieldException,RuntimeException{
            try {
                fireEmployeeById(id);
            }catch(Exception e){
                throw new RuntimeException("Deletion failed",e);
            }
            return new ResponseEntity<>("Deletion Successful",HttpStatus.OK);

    }

    public ResponseEntity<EmpDTO> update(EmpDTO empDTO) throws InvalidFieldException {
        Optional<Employee> employeeOptional = getEmpByID(empDTO.getEmpID());
        if (employeeOptional.isEmpty()) {
            throw new InvalidFieldException("Invalid Employee Id");
        } else {
            Employee employee = employeeOptional.get();
            employeeMapper.updateEmployeeFromDto(empDTO, employee);
            if (empDTO.getDepartmentID() != null) {
                if (!(employee.getDepartmentId().getDepartmentId().equals(empDTO.getDepartmentID()))) {
                    employee.setProjects(null);
                    Optional<Department> departmentOptional=departmentService.getDeptById(empDTO.getDepartmentID());
                    if(departmentOptional.isEmpty()){
                        throw new InvalidFieldException("Department deosn't exist");
                    }
                    else{
//

                        employee.setDepartmentId(departmentOptional.get());

                    }

                }
            }
            if(empDTO.getProjectIDs()!=null){

                List<Project> projectList=projectService.getProjectsByIds(empDTO.getProjectIDs());
                Department department;
                if(empDTO.getDepartmentID()!=null){
                    department=departmentService.getDeptById(empDTO.getDepartmentID()).get();

                } else {
                    department = employee.getDepartmentId();
                }
                if(department==null){
                    throw new InvalidFieldException("Wrong Department");
                }
                else {
                    projectList.removeIf(project -> !project.getDept().equals(department));
                    employee.setProjects(projectList);
                }

            }


            return new ResponseEntity<EmpDTO>(empToDTO(employeeRepo.save(employee)),HttpStatus.ACCEPTED);
        }
    }








}
