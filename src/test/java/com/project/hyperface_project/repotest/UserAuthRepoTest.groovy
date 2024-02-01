package com.project.hyperface_project.repotest

import com.project.hyperface_project.model.UserAuth
import com.project.hyperface_project.repo.UserAuthRepo
import com.project.hyperface_project.util.UserRole

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserAuthRepoTest {
    @Autowired
    private UserAuthRepo userAuthRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void init(){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = [new SimpleGrantedAuthority(UserRole.EMPLOYEE.toString())]
        UserAuth userAuth=new UserAuth("Aneesh",passwordEncoder.encode("secret"),simpleGrantedAuthorities,0);

    }

}
