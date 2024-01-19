package com.project.hyperface_project.mapper;

import com.project.hyperface_project.DTO.EmpDTO;
import com.project.hyperface_project.model.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeeFromDto(EmpDTO empDTO, @MappingTarget Employee entity);
}