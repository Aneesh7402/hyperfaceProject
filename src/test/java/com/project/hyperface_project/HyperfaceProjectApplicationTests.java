package com.project.hyperface_project;

import com.project.hyperface_project.DTO.AuthResponseDTO;
import com.project.hyperface_project.DTO.DeptDTO;
import com.project.hyperface_project.DTO.RegDTO;
import com.project.hyperface_project.controller.DeptController;
import com.project.hyperface_project.controller.UserAuthController;
import com.project.hyperface_project.model.UserAuth;
import com.project.hyperface_project.repo.UserAuthRepo;
import com.project.hyperface_project.util.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.stream.Stream;


@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class HyperfaceProjectApplicationTests {
	DeptDTO deptDTO=new DeptDTO();
	String token;
    private final WebTestClient client;

	private final UserAuthRepo userAuthRepo;

	private final PasswordEncoder passwordEncoder;


	@Autowired
	public HyperfaceProjectApplicationTests(WebTestClient client,UserAuthRepo userAuthRepo,PasswordEncoder passwordEncoder){
		this.client=WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
		this.userAuthRepo=userAuthRepo;
		this.passwordEncoder=passwordEncoder;
	}
	@BeforeEach
	public void setup(){
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = Stream.of(new SimpleGrantedAuthority(UserRole.MANAGER.toString())).toList();
		UserAuth userAuth=new UserAuth("Aneesh",passwordEncoder.encode("password"),simpleGrantedAuthorities,2);

		userAuthRepo.save(userAuth);
		RegDTO regDTO=new RegDTO("Aneesh","password");
        token = client.post().uri("auth/login").contentType(MediaType.APPLICATION_JSON).bodyValue(regDTO).exchange().expectBody(String.class).returnResult().getResponseBody();

	}
	@Test
	public void DepartmentController_insertDepartment_Successful(){

	}




}
