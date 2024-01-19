package com.project.hyperface_project.mapper;

import com.project.hyperface_project.DTO.ProjectDTO;
import com.project.hyperface_project.model.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")

public interface ProjectMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateProjectFromDTO(ProjectDTO projectDTO, @MappingTarget Project project);
}
