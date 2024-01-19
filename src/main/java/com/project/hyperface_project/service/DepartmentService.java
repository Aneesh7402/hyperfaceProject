package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.mapper.DepartmentMapper;
import com.project.hyperface_project.mapper.EmployeeMapper;
import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.model.Employee;
import com.project.hyperface_project.model.Project;
import com.project.hyperface_project.repo.DepartmentRepo;
import com.project.hyperface_project.repo.EmployeeRepo;
import com.project.hyperface_project.repo.ProjectRepo;
import com.project.hyperface_project.DTO.DeptDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class DepartmentService {

    private final DepartmentRepo departmentRepo;




    private final ProjectRepo projectRepo;



    private final EmployeeRepo employeeRepo;


    private final DepartmentMapper departmentMapper;



    public DepartmentService(DepartmentRepo departmentRepo,ProjectRepo projectRepo,EmployeeRepo employeeRepo,DepartmentMapper departmentMapper){
        this.departmentRepo=departmentRepo;

        this.projectRepo=projectRepo;

        this.employeeRepo=employeeRepo;
        this.departmentMapper=departmentMapper;

    }

    //Utility functions
    public List<Department> getAllDepts(){
        return departmentRepo.findAll();
    }
    public Department saveDept(Department department){
        return departmentRepo.save(department);
    }

    public Optional<Department> getDeptById(Integer id){
        return departmentRepo.findById(id.longValue());

    }

    public void deleteProject(Integer id) {
        System.out.println(id);
        projectRepo.deleteById(id.longValue());
    }

    public void deleteDeptbyId(Integer id){
        departmentRepo.deleteById(id.longValue());
    }

    public DeptDTO deptToDTO(Department department){
        List<Integer> empIDs=new ArrayList<>();
        List<Integer> projectIDs=new ArrayList<>();
        for(Employee employee:department.getEmployeeList()){
            empIDs.add(employee.getEmpId());
        }
        for(Project project:department.getProjectList()){
            projectIDs.add(project.getProjectId());
        }
        return new DeptDTO(department.getDepartmentId(),department.getName(),empIDs,projectIDs);
    }

    //Util

    public List<Department> getAllDeptsByIDs(List<Integer> deptList){
        return departmentRepo.findAllById(deptList.stream().map(Integer::longValue).collect(Collectors.toList()));
    }


    //Controller functions
    public ResponseEntity<DeptDTO> saveMyDept(DeptDTO deptInsert) throws NullPointerException{

            Department department=new Department(deptInsert.getDeptName(),null,null);
            return new ResponseEntity<DeptDTO>(deptToDTO(saveDept(department)), HttpStatus.CREATED);

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
    public String fireDept(Integer id) throws InvalidFieldException{
        Optional<Department> departmentOptional=getDeptById(id);
        if(departmentOptional.isEmpty()){
            throw new InvalidFieldException("Invalid id");
        }
        else {
            Department department=departmentOptional.get();
            for (Project proj : department.getProjectList()) {
                deleteProject(proj.getProjectId());
            }
            for (Employee emp : department.getEmployeeList()) {
                emp.setDepartmentId(null);
                employeeRepo.save(emp);
            }

            department.setEmployeeList(null);
            departmentRepo.save(department);
            deleteDeptbyId(id);
            return "Successful";
        }
    }


    public ResponseEntity<DeptDTO> updateDept(DeptDTO deptDTO) throws InvalidFieldException{
        Optional<Department> departmentOptional=getDeptById(deptDTO.getId());
        if (departmentOptional.isEmpty()) {
            throw new InvalidFieldException("Invalid Employee Id");
        } else {
            Department department=departmentOptional.get();
            departmentMapper.updateDepartmentFromDto(deptDTO,department);
            if (deptDTO.getEmpIDs() != null) {
                List<Employee> employeeAddList=new ArrayList<>();
                for(Integer empId:deptDTO.getEmpIDs()) {
                    Optional<Employee> employeeOptional=employeeRepo.findById(empId.longValue());
                    employeeOptional.ifPresent(employeeAddList::add);
                }

                List<Employee> employeeRemoveList=department.getEmployeeList();
                List<Employee> employeeAdd=new ArrayList<>(employeeAddList);
                employeeAdd.removeAll(employeeRemoveList);
                employeeRemoveList.removeAll(employeeAddList);

                for(Employee employee:employeeAdd){
                    employee.setProjects(null);
                    employee.setDepartmentId(department);
                    employeeRepo.save(employee);
                }
                for(Employee employee:employeeRemoveList){
                    employee.setProjects(null);
                    employee.setDepartmentId(null);
                    employeeRepo.save(employee);
                }

                }
                return new ResponseEntity<>(deptToDTO(saveDept(department)),HttpStatus.ACCEPTED);
            }
//            if(deptDTO.getProjectIDs()!=null){
//                List<Project> projectList=new ArrayList<>();
//
//                for(Integer projectId:empDTO.getProjectIDs()){
//                    Optional<Project> projectOptional=projectService.getProjectById(projectId);
//                    if(projectOptional.isPresent()){
//                        Project project=projectOptional.get();
//                        Integer departmentId;
//                        if(empDTO.getDepartmentID()!=null){
//                            departmentId=empDTO.getDepartmentID();
//                        }
//                        else{
//                            departmentId=employee.getDepartmentId().getDepartmentId();
//                        }
//                        if(project.getDept().getDepartmentId().equals(departmentId)){
//                            projectList.add(project);
//                        }
//
//                    }
//                }
//                employee.setProjects(projectList);
//
//            }
//            employeeRepo.save(employee);
//
//            return new ResponseEntity<EmpDTO>(empDTO,HttpStatus.ACCEPTED);

    }
}
