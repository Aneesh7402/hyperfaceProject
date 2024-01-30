package com.project.hyperface_project.controller;

import com.project.hyperface_project.DTO.AuthResponseDTO;
import com.project.hyperface_project.DTO.RegDTO;
import com.project.hyperface_project.exceptions.InvalidPriorityAssignmentException;
import com.project.hyperface_project.exceptions.InvalidRoleAssignmentException;
import com.project.hyperface_project.model.UserAuth;
import com.project.hyperface_project.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public UserAuthController(CustomUserDetailService customUserDetailService){

        this.customUserDetailService=customUserDetailService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegDTO regDTO){
        return customUserDetailService.register(regDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody RegDTO regDTO) {
        return customUserDetailService.login(regDTO);

    }

    @GetMapping("/majorPromotion/{username}")
    public ResponseEntity<UserAuth> changeRole(@PathVariable("username") String username)throws InvalidRoleAssignmentException {
        return customUserDetailService.majorPromotion(username);
    }
    @GetMapping("/minorPromotion/{username}")
    public ResponseEntity<UserAuth> changePriority(@PathVariable("username") String username)throws InvalidPriorityAssignmentException {
        return customUserDetailService.minorPromotion(username);
    }


}
