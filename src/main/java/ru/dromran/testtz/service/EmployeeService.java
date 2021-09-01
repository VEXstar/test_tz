package ru.dromran.testtz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dromran.testtz.dto.ResponseMessageDTO;
import ru.dromran.testtz.dto.UserFormDTO;
import ru.dromran.testtz.entity.DepartmentEntity;
import ru.dromran.testtz.entity.EmployeeDepartmentEntity;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.entity.PostEntity;
import ru.dromran.testtz.entity.composite.EmployeeDepartmentId;
import ru.dromran.testtz.exception.BadRequestException;
import ru.dromran.testtz.exception.ForbiddenRequestException;
import ru.dromran.testtz.exception.NotFoundException;
import ru.dromran.testtz.repository.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ru.dromran.testtz.constants.RoleConstants.ADMIN_ROLE;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    AuthService authService;
    @Autowired
    DepartmentEntityRepository departmentEntityRepository;
    @Autowired
    EmployeeEntityRepository employeeEntityRepository;
    @Autowired
    PostEntityRepository postEntityRepository;
    @Autowired
    OrganizationEntityRepository organizationEntityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmployeeDepartmentEntityRepository employeeDepartmentEntityRepository;

    public EmployeeEntity createUser(UserFormDTO userFormDTO) {

        DepartmentEntity depForEmployee = departmentEntityRepository
                .findById(userFormDTO.getDepartmentId())
                .orElse(null);
        EmployeeEntity userBySession = authService.getUserBySession();

        if (userFormDTO.getPassword() == null || userFormDTO.getPassword().isEmpty()) {
            BadRequestException badRequestException = new BadRequestException("Password must be not empty");
            log.error("Try create user with empty password", badRequestException);
            throw badRequestException;
        }

        if (depForEmployee == null) {
            NotFoundException notFoundException = new NotFoundException("Department not found");
            log.error("Cant found department", notFoundException);
            throw notFoundException;
        }

        if (!depForEmployee.getChef().getId().equals(userBySession.getId())) {
            if (!depForEmployee.getOrganization().getChef().getId().equals(userBySession.getId())) {
                if (!userBySession.getRole().equals(ADMIN_ROLE)) {
                    ForbiddenRequestException forbiddenRequestException =
                            new ForbiddenRequestException("You dont have enough permissions to perform this operation");
                    log.error("Try create employee from non admin/chef account", forbiddenRequestException);
                    throw forbiddenRequestException;
                }
            }
        }

        PostEntity post = postEntityRepository.findById(userFormDTO.getPostId()).orElse(null);

        if (post == null) {
            NotFoundException notFoundException = new NotFoundException("Post not found");
            log.error("Cant found post", notFoundException);
            throw notFoundException;
        }

        EmployeeEntity employeeEntityByLogin =
                employeeEntityRepository.findEmployeeEntitiesByLogin(userFormDTO.getLogin());

        if (employeeEntityByLogin != null) {
            BadRequestException badRequestException = new BadRequestException("Login already used");
            log.error("Try create user with used login", badRequestException);
            throw badRequestException;
        }

        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName(userFormDTO.getFirstName());
        employee.setLastName(userFormDTO.getLastName());
        employee.setMiddleName(userFormDTO.getMiddleName());
        employee.setLogin(userFormDTO.getLogin());
        employee.setPassword(passwordEncoder.encode(userFormDTO.getPassword()));
        employee.setPost(post);
        employee.setRole(userFormDTO.getRole());
        EmployeeEntity save = employeeEntityRepository.save(employee);
        EmployeeDepartmentEntity ede = new EmployeeDepartmentEntity();
        ede.setEmployeeDepartmentId(new EmployeeDepartmentId(depForEmployee.getId(), save.getId()));
        employeeDepartmentEntityRepository.save(ede);
        return save;
    }


    private boolean compareTerms(String string, String subString) {
        String toCheck = subString == null ? "" : subString.toLowerCase(Locale.ROOT);
        return string.toLowerCase(Locale.ROOT).contains(toCheck) || toCheck.contains(string.toLowerCase(Locale.ROOT));
    }

    @Transactional
    public Page<EmployeeEntity> findUsers(String firstNameTerm,
                                          String lastNameTerm,
                                          String middleNameTerm,
                                          Long postId,
                                          Long departmentId,
                                          Long organizationId,
                                          String login,
                                          Integer page,
                                          Integer pageSize) {
        int realPage = page == null ? 0 : page;
        int realSize = pageSize == null ? 10 : pageSize;

        Pageable pageable = PageRequest.of(realPage, realSize);

        List<EmployeeEntity> filtered = employeeEntityRepository.findAll().parallelStream().filter(employee -> {
            boolean fNameCheck = compareTerms(employee.getFirstName(), firstNameTerm);
            boolean mNameCheck = compareTerms(employee.getMiddleName(), middleNameTerm);
            boolean lNameCheck = compareTerms(employee.getLastName(), lastNameTerm);
            boolean postCheck = postId == null || employee.getPost().getPostId().equals(postId);
            boolean depCheck = departmentId == null || employee.getDepartment().getId().equals(departmentId);
            boolean orgCheck = organizationId == null || employee.getDepartment().getOrganizationId().equals(organizationId);
            boolean loginCheck = compareTerms(employee.getLogin(), login);
            return fNameCheck && mNameCheck && lNameCheck && postCheck && depCheck && orgCheck && loginCheck;
        }).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());

        return new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());
    }

    public EmployeeEntity getById(Long id) {
        EmployeeEntity byId = employeeEntityRepository.findById(id).orElse(null);
        if (byId == null) {
            NotFoundException notFoundException = new NotFoundException("User not found");
            log.error("Cant found user", notFoundException);
            throw notFoundException;
        }
        return byId;
    }

    public EmployeeEntity updateUser(Long id, UserFormDTO userFormDTO) {
        EmployeeEntity userBySession = authService.getUserBySession();
        EmployeeEntity userById = employeeEntityRepository.findById(id).orElse(null);
        EmployeeEntity employeeEntityByLogin = employeeEntityRepository.findEmployeeEntitiesByLogin(userFormDTO.getLogin());

        if (userById == null) {
            NotFoundException notFoundException = new NotFoundException("User not found");
            log.error("Cant found user", notFoundException);
            throw notFoundException;
        }

        if (employeeEntityByLogin != null && !userById.getLogin().equals(employeeEntityByLogin.getLogin())) {
            BadRequestException badRequestException = new BadRequestException("Login already used");
            log.error("Try update user exists login", badRequestException);
            throw badRequestException;
        }

        if (!userBySession.getId().equals(id)) {
            if (!userBySession.getRole().equals(ADMIN_ROLE)) {
                if (!userById.getDepartment().getChef().getId().equals(userBySession.getId())) {
                    if (!userById.getDepartment().getOrganization().getChef().getId().equals(userBySession.getId())) {
                        ForbiddenRequestException forbiddenRequestException =
                                new ForbiddenRequestException(
                                        "You dont have enough permissions to perform this operation");
                        log.error("Try update employee from non admin/chef account", forbiddenRequestException);
                        throw forbiddenRequestException;
                    }
                }
            }
        }

        if (userBySession.getId().equals(id)) {
            userById.setLogin(userFormDTO.getLogin());
            userById.setPassword(userFormDTO.getPassword() == null ? userById.getPassword() :
                    userFormDTO.getPassword());
            userById.setFirstName(userFormDTO.getFirstName());
            userById.setLastName(userFormDTO.getLastName());
            userById.setMiddleName(userFormDTO.getMiddleName());
        } else {
            DepartmentEntity department = departmentEntityRepository.findById(userFormDTO.getDepartmentId())
                    .orElse(null);
            PostEntity post = postEntityRepository.findById(userFormDTO.getPostId()).orElse(null);

            if (department == null) {
                NotFoundException notFoundException = new NotFoundException("Department not found");
                log.error("Cant found department", notFoundException);
                throw notFoundException;
            }

            if (post == null) {
                NotFoundException notFoundException = new NotFoundException("Post not found");
                log.error("Cant found post", notFoundException);
                throw notFoundException;
            }

            userById.setLogin(userFormDTO.getLogin());
            userById.setPassword(userFormDTO.getPassword() == null ? userById.getPassword() :
                    userFormDTO.getPassword());
            userById.setFirstName(userFormDTO.getFirstName());
            userById.setLastName(userFormDTO.getLastName());
            userById.setMiddleName(userFormDTO.getMiddleName());
            userById.setRole(userFormDTO.getRole());
            userById.setPost(post);
            if (!userById.getDepartment().getId().equals(department.getId())) {
                EmployeeDepartmentEntity employeeDepartment =
                        employeeDepartmentEntityRepository
                                .getById(new EmployeeDepartmentId(userById.getDepartment().getId(), userById.getId()));
                employeeDepartmentEntityRepository.delete(employeeDepartment);
                employeeDepartment = new EmployeeDepartmentEntity();

                employeeDepartment.setEmployeeDepartmentId(new EmployeeDepartmentId(department.getId(),
                        userById.getId()));

                employeeDepartmentEntityRepository.save(employeeDepartment);
            }
        }
        return employeeEntityRepository.save(userById);

    }

    public ResponseMessageDTO deleteUser(Long id) {

        EmployeeEntity userBySession = authService.getUserBySession();

        if (!userBySession.getRole().equals(ADMIN_ROLE)) {
            ForbiddenRequestException forbiddenRequestException =
                    new ForbiddenRequestException("You dont have enough permissions to perform this operation");
            log.error("Try delete employee from non admin account", forbiddenRequestException);
            throw forbiddenRequestException;
        }

        EmployeeEntity employeeEntityById = employeeEntityRepository.findById(id).orElse(null);

        if (employeeEntityById == null) {
            NotFoundException notFoundException = new NotFoundException("User not found");
            log.error("Cant found user", notFoundException);
            throw notFoundException;
        }
        employeeEntityRepository.delete(employeeEntityById);
        return ResponseMessageDTO.getMessage("Deleted");
    }
}
