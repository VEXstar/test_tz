package ru.dromran.testtz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.dto.UserFormDTO;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.service.EmployeeService;

import javax.validation.Valid;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;
import static ru.dromran.testtz.constants.RoleConstants.USER_ROLE;

@RestController
@RequestMapping("/api/employee")
@Secured({USER_ROLE, ADMIN_ROLE})
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("")
    ResponseEntity<EmployeeEntity> createUser(@RequestBody @Valid UserFormDTO userFormDTO) {
        return ResponseEntity.ok(employeeService.createUser(userFormDTO));
    }

    @GetMapping("")
    ResponseEntity<Page<EmployeeEntity>> findUsers(@RequestParam(required = false) String firstNameTerm,
                                                   @RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer pageSize,
                                                   @RequestParam(required = false) String lastNameTerm,
                                                   @RequestParam(required = false) String middleNameTerm,
                                                   @RequestParam(required = false) Integer postId,
                                                   @RequestParam(required = false) Long departmentId,
                                                   @RequestParam(required = false) Long organizationId,
                                                   @RequestParam(required = false) String login) {
        return ResponseEntity.ok(employeeService.findUsers(firstNameTerm,
                lastNameTerm, middleNameTerm, postId, departmentId, organizationId, login, page, pageSize));
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeEntity> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<EmployeeEntity> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UserFormDTO userFormDTO) {
        return ResponseEntity.ok(employeeService.updateUser(id, userFormDTO));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessageDTO> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.deleteUser(id));
    }
}
