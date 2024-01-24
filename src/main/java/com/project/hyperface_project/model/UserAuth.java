package com.project.hyperface_project.model;

import com.project.hyperface_project.util.UserRole;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserAuth")
@Builder
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slNO;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToOne
    @JoinColumn(name="empID")
    private Employee employeeDetails;
}
