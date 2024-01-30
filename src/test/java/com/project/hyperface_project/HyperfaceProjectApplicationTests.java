package com.project.hyperface_project;

import com.project.hyperface_project.DTO.AuthResponseDTO;
import com.project.hyperface_project.DTO.DeptDTO;
import com.project.hyperface_project.DTO.RegDTO;
import com.project.hyperface_project.controller.DeptController;
import com.project.hyperface_project.controller.UserAuthController;
import com.project.hyperface_project.repo.UserAuthRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@AutoConfigureMockMvc

class HyperfaceProjectApplicationTests {
	DeptDTO deptDTO=new DeptDTO();
	@BeforeEach
			public void setup(){

	}

	deptDTO.set()
//	WebTestClient webTestClient=WebTestClient.bindToServer()
//		.baseUrl("http://localhost:8080")
//		.build()
//		.post()
//		.uri("/dept/insertDepartment")
//		.bodyValue("{\"username\":\"aneesh\",
//				\"password\":\"abc\",
//			\"deptName\":\"newDept\"}")
//		.exchange()
//		.expectStatus().isCreated()
//		.expectHeader().val


}
