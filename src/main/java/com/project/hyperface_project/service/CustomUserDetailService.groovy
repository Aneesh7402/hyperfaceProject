package com.project.hyperface_project.service;

import com.project.hyperface_project.DTO.AuthResponseDTO;
import com.project.hyperface_project.DTO.RegDTO
import com.project.hyperface_project.exceptions.InvalidPriorityAssignmentException;
import com.project.hyperface_project.exceptions.InvalidRoleAssignmentException;
import com.project.hyperface_project.model.UserAuth;
import com.project.hyperface_project.repo.UserAuthRepo
import com.project.hyperface_project.security.JwtTokenGenerator;
import com.project.hyperface_project.util.UserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service

import java.util.stream.Stream;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserAuthRepo userAuthRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CustomUserDetailService(UserAuthRepo userAuthRepo,@Lazy AuthenticationManager authenticationManager, JwtTokenGenerator jwtTokenGenerator, PasswordEncoder passwordEncoder){
       this.userAuthRepo=userAuthRepo;
       this.authenticationManager=authenticationManager;
       this.jwtTokenGenerator=jwtTokenGenerator;
       this.passwordEncoder=passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            UserAuth userAuth = userAuthRepo.findByUsername(username).orElseThrow({ ->
                new UsernameNotFoundException("Username not found") });
            System.out.println("iaosdih1231");
            return userAuth;
        }catch (Exception e){
            System.out.println("iaosdih");
            throw new UsernameNotFoundException("ojasodj",e);
        }
    }

    public ResponseEntity<AuthResponseDTO> login(RegDTO regDTO){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        regDTO.getUsername(),regDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    public ResponseEntity<String> register(RegDTO regDTO){
        if(userAuthRepo.existsByUsername(regDTO.getUsername())){
            return new ResponseEntity<>("Username exists", HttpStatus.BAD_REQUEST);
        }
        else{
            System.out.println(regDTO.getUsername());
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = [new SimpleGrantedAuthority(UserRole.EMPLOYEE.toString())]
            UserAuth userAuth=new UserAuth(regDTO.getUsername(),passwordEncoder.encode(regDTO.getPassword()),simpleGrantedAuthorities,0);
            UserAuth savedUserAuth=userAuthRepo.save(userAuth);
            System.out.println(userAuth)
            return new ResponseEntity<>("Successful",HttpStatus.CREATED);
        }
    }
    public ResponseEntity<UserAuth> majorPromotion(String username)throws UsernameNotFoundException,InvalidRoleAssignmentException{
        UserAuth userAuth = userAuthRepo.findByUsername(username).orElseThrow({ ->
            new UsernameNotFoundException("Username not found") });
        String userAuthority=userAuth.getAuthorities().get(0).toString()
        switch (userAuthority){
            case UserRole.BOARD_MEMBER.toString():
                throw new InvalidRoleAssignmentException("Already has highest designation")
            case UserRole.OTHER.toString():
                throw new InvalidRoleAssignmentException("Not an employee")
            case UserRole.EMPLOYEE.toString():
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = [new SimpleGrantedAuthority(UserRole.MANAGER.toString())]
                userAuth.setPriority(0)
                userAuth.setAuthorities(simpleGrantedAuthorities)
                break
            case UserRole.MANAGER.toString():
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = [new SimpleGrantedAuthority(UserRole.BOARD_MEMBER.toString())]
                userAuth.setPriority(0)
                userAuth.setAuthorities(simpleGrantedAuthorities)
                break
            default:
                throw new InvalidRoleAssignmentException("Unexpected error")
                break
        }
        return new ResponseEntity<UserAuth>(userAuthRepo.save(userAuth),HttpStatus.OK)

    }
    public ResponseEntity<UserAuth> minorPromotion(String username) throws InvalidPriorityAssignmentException{
        UserAuth userAuth = userAuthRepo.findByUsername(username).orElseThrow({ ->
            new UsernameNotFoundException("Username not found") });
        if(userAuth.getPriority()<4) {
            userAuth.setPriority(userAuth.getPriority() + 1)
            return new ResponseEntity<UserAuth>(userAuthRepo.save(userAuth),HttpStatus.OK)
        }
        else{
            throw new InvalidPriorityAssignmentException("Already has maximum priority for role")
        }

    }
}
