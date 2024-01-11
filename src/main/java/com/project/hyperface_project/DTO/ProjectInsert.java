package com.project.hyperface_project.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProjectInsert {
    private String name;
    private Integer deptId;

    public void setName(String name) {
        this.name = name;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public Integer getDeptId() {
        return deptId;
    }
}
