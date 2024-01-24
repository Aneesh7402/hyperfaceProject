package com.project.hyperface_project.DTO;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String authToken;
    private String tokenType="Bearer ";
    public AuthResponseDTO(String authToken){
        this.authToken=authToken;
    }
}
