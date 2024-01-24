package com.project.hyperface_project.controller;

import com.project.hyperface_project.DTO.AuthResponseDTO;
import com.project.hyperface_project.DTO.RegDTO;
import com.project.hyperface_project.model.UserAuth;
import com.project.hyperface_project.repo.UserAuthRepo;
import com.project.hyperface_project.security.JwtTokenGenerator;
import com.project.hyperface_project.security.SecurityConfig;
import com.project.hyperface_project.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private final UserAuthRepo userAuthRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public UserAuthController(UserAuthRepo userAuthRepo,AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder,JwtTokenGenerator jwtTokenGenerator){
        this.userAuthRepo=userAuthRepo;
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.jwtTokenGenerator=jwtTokenGenerator;
    }
    @PostMapping("/register")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> register(@RequestBody RegDTO regDTO){
        if(userAuthRepo.existsByUsername(regDTO.getUsername())){
            return new ResponseEntity<>("Username exists", HttpStatus.BAD_REQUEST);
        }
        else{
            System.out.println(regDTO.getPassword());
            UserAuth userAuth=UserAuth.builder().username(
                    regDTO.getUsername()).password(passwordEncoder.encode(regDTO.getPassword())).role(UserRole.MANAGER).build();
            userAuthRepo.save(userAuth);
            return new ResponseEntity<>("Successful",HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody RegDTO regDTO) throws UnsupportedEncodingException {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        regDTO.getUsername(),regDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token),HttpStatus.OK);

    }

}
