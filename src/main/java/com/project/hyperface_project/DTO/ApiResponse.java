package com.project.hyperface_project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.util.Map;



@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ApiResponse {
    private Integer code;
    private String header;
    private Map<String, Object> body;
    private Instant timestamp;


}
