package ru.dromran.testtz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dromran.testtz.dto.DepartmentFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.DepartmentEntity;
import ru.dromran.testtz.service.DepartmentService;

import javax.validation.Valid;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;
import static ru.dromran.testtz.constants.RoleConstants.USER_ROLE;

@RestController
@RequestMapping("/api/department")
@Secured({USER_ROLE, ADMIN_ROLE})
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("")
    ResponseEntity<DepartmentEntity> createPost(@RequestBody @Valid DepartmentFormDTO departmentFormDTO) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentFormDTO));
    }

    @GetMapping("")
    ResponseEntity<Page<DepartmentEntity>> findDepartments(@RequestParam(required = false) Integer page,
                                                           @RequestParam(required = false) Integer size,
                                                           @RequestParam(required = false) String nameTerm,
                                                           @RequestParam(required = false) Long chefId,
                                                           @RequestParam(required = false) Long organizationId) {
        return ResponseEntity.ok(departmentService.findDepartments(page, size, nameTerm, chefId, organizationId));

    }

    @GetMapping("/{id}")
    ResponseEntity<DepartmentEntity> getDeparment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<DepartmentEntity> updateDepartment(@PathVariable("id") Long id,
                                                      @RequestBody @Valid DepartmentFormDTO departmentFormDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentFormDTO));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessageDTO> deleteDeparment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }
}
