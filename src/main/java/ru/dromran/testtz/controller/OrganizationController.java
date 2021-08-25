package ru.dromran.testtz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.dromran.testtz.dto.OrganizationFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.OrganizationEntity;
import ru.dromran.testtz.service.OrganizationService;

import javax.validation.Valid;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;
import static ru.dromran.testtz.constants.RoleConstants.USER_ROLE;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @PostMapping("")
    @Secured(ADMIN_ROLE)
    ResponseEntity<OrganizationEntity> createOrganization(@RequestBody @Valid
                                                                  OrganizationFormDTO organizationFormDTO) {
        return ResponseEntity.ok(organizationService.createOrganization(organizationFormDTO));
    }

    @GetMapping("")
    @Secured({USER_ROLE, ADMIN_ROLE})
    ResponseEntity<Page<OrganizationEntity>> findOrganizations(@RequestParam(required = false) Integer page,
                                                               @RequestParam(required = false) Integer pageSize,
                                                               @RequestParam(required = false) String nameTerm,
                                                               @RequestParam(required = false) String address,
                                                               @RequestParam(required = false) Long chefId) {
        return ResponseEntity.ok(organizationService.findOrganizations(page, pageSize, nameTerm, address, chefId));
    }

    @GetMapping("/{id}")
    @Secured({USER_ROLE, ADMIN_ROLE})
    ResponseEntity<OrganizationEntity> getOrganizationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(organizationService.getOrganizationById(id));
    }

    @PutMapping("/{id}")
    @Secured({USER_ROLE, ADMIN_ROLE})
    ResponseEntity<OrganizationEntity>
    updateOrganization(@PathVariable("id") Long id,
                       @RequestBody @Valid OrganizationFormDTO organizationFormDTO) {
        return ResponseEntity.ok(organizationService.updateOrganization(id, organizationFormDTO));

    }

    @DeleteMapping("/{id}")
    @Secured({USER_ROLE, ADMIN_ROLE})
    ResponseEntity<ResponseMessageDTO> deleteOrganization(@PathVariable("id") Long id) {
        return ResponseEntity.ok(organizationService.deleteOrganization(id));
    }
}
