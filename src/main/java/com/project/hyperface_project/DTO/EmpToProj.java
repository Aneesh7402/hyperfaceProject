package com.project.hyperface_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter


public class EmpToProj {
    private Integer projId;
    private List<Integer> empId;
}
