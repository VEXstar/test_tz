package ru.dromran.testtz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dromran.testtz.dto.LoginFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    ResponseEntity<EmployeeEntity> loginUser(@RequestBody @Valid LoginFormDTO loginFormDTO) {
        return ResponseEntity.ok(authService.loginUser(loginFormDTO));
    }

    @GetMapping("/logout")
    ResponseEntity<ResponseMessageDTO> logout() {
        authService.logoutUser();
        return ResponseMessageDTO.getResponseEntity(HttpStatus.OK, "session invalidated");
    }
}
