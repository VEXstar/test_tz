package ru.dromran.testtz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dromran.testtz.dto.DepartmentFormDTO;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.entity.DepartmentEntity;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.OrganizationEntity;
import ru.dromran.testtz.exception.ForbiddenRequestException;
import ru.dromran.testtz.exception.NotFoundException;
import ru.dromran.testtz.repository.DepartmentEntityRepository;
import ru.dromran.testtz.repository.EmployeeEntityRepository;
import ru.dromran.testtz.repository.OrganizationEntityRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    AuthService authService;

    @Autowired
    OrganizationEntityRepository organizationEntityRepository;

    @Autowired
    EmployeeEntityRepository employeeEntityRepository;

    @Autowired
    DepartmentEntityRepository departmentEntityRepository;


    public DepartmentEntity createDepartment(DepartmentFormDTO departmentFormDTO) {
        EmployeeEntity userBySession = authService.getUserBySession();

        OrganizationEntity organizationForDep =
                organizationEntityRepository.findById(departmentFormDTO.getOrganizationId()).orElse(null);

        if (organizationForDep == null) {
            NotFoundException notFoundException = new NotFoundException("Organization not found");
            log.error("Cant found organization", notFoundException);
            throw notFoundException;
        }

        EmployeeEntity chef = employeeEntityRepository.findById(departmentFormDTO.getChefId()).orElse(null);

        if (chef == null) {
            NotFoundException notFoundException = new NotFoundException("User not found");
            log.error("Cant found user", notFoundException);
            throw notFoundException;
        }

        if (!organizationForDep.getChef().getId().equals(userBySession.getId()) &&
                !userBySession.getRole().equals(ADMIN_ROLE)) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("You dont have enough permissions to perform this operation");
            log.error("Try create department from non admin/chef account", forbiddenRequestException);
            throw forbiddenRequestException;
        }
        DepartmentEntity department = new DepartmentEntity();
        department.setMail(departmentFormDTO.getMail());
        department.setName(departmentFormDTO.getName());
        department.setOrganizationId(organizationForDep.getId());
        department.setPhone(departmentFormDTO.getPhoneNumber());
        department.setChefId(chef.getId());
        return departmentEntityRepository.save(department);

    }

    private boolean compareTerms(String string, String subString) {
        String toCheck = subString == null ? "" : subString.toLowerCase(Locale.ROOT);
        return string.toLowerCase(Locale.ROOT).contains(toCheck) || toCheck.contains(string.toLowerCase(Locale.ROOT));
    }

    public Page<DepartmentEntity> findDepartments(Integer page,
                                                  Integer size,
                                                  String nameTerm,
                                                  Long chefId,
                                                  Long organizationId) {
        int realPage = page == null ? 0 : page;
        int realSize = size == null ? 10 : size;
        Pageable pageable = PageRequest.of(realPage, realSize);

        List<DepartmentEntity> filtered = departmentEntityRepository
                .findAll()
                .parallelStream()
                .filter(departmentEntity -> {
                    boolean orgCheck = organizationId == null || departmentEntity.getOrganizationId().equals(organizationId);
                    boolean chefCheck = chefId == null || departmentEntity.getChef().getId().equals(chefId);
                    boolean termCheck = compareTerms(departmentEntity.getName(), nameTerm);
                    return orgCheck && chefCheck && termCheck;
                }).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public DepartmentEntity getDepartmentById(Long id) {
        DepartmentEntity byId = departmentEntityRepository.findById(id).orElse(null);
        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("Department not found");
            log.error("Cant found department", notFoundException);
            throw notFoundException;
        }
        return byId;
    }

    public ResponseMessageDTO deleteDepartment(Long id) {
        DepartmentEntity byId = departmentEntityRepository.findById(id).orElse(null);
        EmployeeEntity userBySession = authService.getUserBySession();
        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("Department not found");
            log.error("Cant found department", notFoundException);
            throw notFoundException;
        }

        if (!byId.getChef().getId().equals(userBySession.getId())) {
            if (!byId.getOrganization().getChef().getId().equals(userBySession.getId())) {
                if (!userBySession.getRole().equals(ADMIN_ROLE)) {
                    ForbiddenRequestException forbiddenRequestException =
                            new ForbiddenRequestException("You dont have enough permissions to perform this operation");
                    log.error("Try delete department from non admin/chef account", forbiddenRequestException);
                    throw forbiddenRequestException;
                }
            }
        }
        departmentEntityRepository.delete(byId);
        return ResponseMessageDTO.getMessage("Deleted");
    }

    public DepartmentEntity updateDepartment(Long id,
                                             DepartmentFormDTO departmentFormDTO) {
        DepartmentEntity byId = departmentEntityRepository.findById(id).orElse(null);
        EmployeeEntity userBySession = authService.getUserBySession();
        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("Department not found");
            log.error("Cant found department", notFoundException);
            throw notFoundException;
        }

        if (!byId.getChef().getId().equals(userBySession.getId())) {
            if (!byId.getOrganization().getChef().getId().equals(userBySession.getId())) {
                if (!userBySession.getRole().equals(ADMIN_ROLE)) {
                    ForbiddenRequestException forbiddenRequestException =
                            new ForbiddenRequestException("You dont have enough permissions to perform this operation");
                    log.error("Try update department from non admin/chef account", forbiddenRequestException);
                    throw forbiddenRequestException;
                }
            }
        }
        EmployeeEntity chef = employeeEntityRepository.findById(departmentFormDTO.getChefId()).orElse(null);
        if (chef == null) {
            NotFoundException notFoundException = new NotFoundException("User not found");
            log.error("Cant found user", notFoundException);
            throw notFoundException;
        }
        OrganizationEntity organizationForDep =
                organizationEntityRepository.findById(departmentFormDTO.getOrganizationId()).orElse(null);
        if (organizationForDep == null) {
            NotFoundException notFoundException = new NotFoundException("Organization not found");
            log.error("Cant found organization", notFoundException);
            throw notFoundException;
        }

        byId.setChefId(chef.getId());
        byId.setMail(departmentFormDTO.getMail());
        byId.setPhone(departmentFormDTO.getPhoneNumber());
        byId.setName(departmentFormDTO.getName());
        byId.setOrganizationId(organizationForDep.getId());
        return departmentEntityRepository.save(byId);
    }
}
