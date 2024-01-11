package com.project.hyperface_project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class DeptInsert {
    @Size(max=20,message="Field length 10-20 characters")
    private String username;

    @Size(max=20,message="Max Field length 20 characters")
    private String password;
    @NotBlank(message = "cannot be null")
    private String deptName;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDeptName() {
        return deptName;
    }
}
