package com.project.hyperface_project.mapper;

import com.project.hyperface_project.DTO.DeptDTO;
import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.model.Department;
import com.project.hyperface_project.model.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")

public interface DepartmentMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentFromDto(DeptDTO deptDTO, @MappingTarget Department department);
}
