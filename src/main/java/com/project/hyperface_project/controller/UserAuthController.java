package com.project.hyperface_project.controller;

import com.project.hyperface_project.DTO.AuthResponseDTO;
import com.project.hyperface_project.DTO.RegDTO;
import com.project.hyperface_project.exceptions.InvalidFieldException;
import com.project.hyperface_project.exceptions.InvalidPriorityAssignmentException;
import com.project.hyperface_project.exceptions.InvalidRoleAssignmentException;
import com.project.hyperface_project.model.UserAuth;
import com.project.hyperface_project.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public UserAuthController(CustomUserDetailService customUserDetailService){

        this.customUserDetailService=customUserDetailService;
    }
    @PostMapping("/registerCEO")
    public ResponseEntity<String> registerCEO(@RequestBody RegDTO regDTO){
        return customUserDetailService.registerCEO(regDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody RegDTO regDTO) {
        return customUserDetailService.login(regDTO);

    }
    @PostMapping("/registerOther")
    @PreAuthorize("hasAuthority('BOARD_MEMBER')")
    public ResponseEntity<String> registerOther(@RequestBody RegDTO regDTO){
        return customUserDetailService.registerOther(regDTO);
    }
    @PostMapping("/registerEmp")
    @PreAuthorize("hasAnyAuthority('BOARD_MEMBER','MANAGER')")
    public ResponseEntity<String> registerEMP(@RequestBody RegDTO regDTO) throws InvalidFieldException {
        return customUserDetailService.registerEmp(regDTO);
    }

    @GetMapping("/majorPromotion/{username}")
    @PreAuthorize("hasAuthority('BOARD_MEMBER')")
    public ResponseEntity<UserAuth> changeRole(@PathVariable("username") String username)throws InvalidRoleAssignmentException {
        return customUserDetailService.majorPromotion(username);
    }
    @GetMapping("/minorPromotionEmployee/{username}")
    @PreAuthorize("hasAnyAuthority('BOARD_MEMBER','MANAGER')")
    public ResponseEntity<UserAuth> changePriorityEmp(@PathVariable("username") String username)throws InvalidPriorityAssignmentException {
        return customUserDetailService.minorPromotion(username);
    }
//    @GetMapping("/minorPromotionEmployee/{username}")
//    @PreAuthorize("hasAuthority('BOARD_MEMBER')")
//    public ResponseEntity<UserAuth> changePriorityManager(@PathVariable("username") String username)throws InvalidPriorityAssignmentException {
//        return customUserDetailService.minorPromotion(username);
//    }
//
//    @GetMapping("/minorPromotionEmployee/{username}")
//    @PreAuthorize("hasAuthority('BOARD_MEMBER') and principal.getPriority()==5")
//    public ResponseEntity<UserAuth> changePriorityBoardMember(@PathVariable("username") String username)throws InvalidPriorityAssignmentException {
//        return customUserDetailService.minorPromotion(username);
//    }


}
