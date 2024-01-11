package com.project.hyperface_project.DTO;

import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmpDTO {
    private Integer empID;
    private String name;
    private Integer departmentID;
    private List<idk> projectIDs;

}
