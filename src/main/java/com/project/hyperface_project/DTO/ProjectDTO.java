package com.project.hyperface_project.DTO;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectDTO {

    private Integer projectId;
    private String name;
    private Integer deptId;
    private List<Integer> employeeIds;
}
