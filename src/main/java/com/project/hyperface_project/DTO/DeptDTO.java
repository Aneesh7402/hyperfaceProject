package com.project.hyperface_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DeptDTO {

    private Integer id;

    private String deptName;

    private List<Integer> empIDs;

    private List<Integer> projectIDs;


}
