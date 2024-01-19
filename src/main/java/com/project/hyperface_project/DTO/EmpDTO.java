package com.project.hyperface_project.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmpDTO {
    private Integer empID;
    @NotNull
    private String name;
    private Integer departmentID;
    private List<Integer> projectIDs;

}
