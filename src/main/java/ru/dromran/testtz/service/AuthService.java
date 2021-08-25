package ru.dromran.testtz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.dromran.testtz.dto.LoginFormDTO;
import ru.dromran.testtz.dto.SessionUserDTO;
import ru.dromran.testtz.entity.EmployeeEntity;
import ru.dromran.testtz.exception.NotFoundException;
import ru.dromran.testtz.repository.EmployeeEntityRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static ru.dromran.testtz.constants.RoleConstants.ANONYMOUS_ROLE;
import static ru.dromran.testtz.constants.RoleConstants.USER_ROLE;

@Service
@Slf4j
public class AuthService {


    @Autowired
    EmployeeEntityRepository employeeEntityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    private HttpSession getCurrentSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }


    private Authentication authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(ANONYMOUS_ROLE));
            AnonymousAuthenticationToken authenticationToken =
                    new AnonymousAuthenticationToken(Long.toString(System.currentTimeMillis()), new Object(), authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        return authentication;
    }

    private void authenticate(Long userId, String roleSet) {
        roleSet = roleSet == null ? USER_ROLE : roleSet;
        Authentication oldAuth = authentication();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleSet));

        SessionUserDTO sessionUser = new SessionUserDTO(userId.toString(), authorities);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(sessionUser, userId, authorities);
        authenticationToken.setDetails(oldAuth.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }

    public EmployeeEntity loginUser(LoginFormDTO loginFormDTO) {
        EmployeeEntity employee =
                employeeEntityRepository.getEmployeeEntityByLogin(loginFormDTO.getLogin());
        if (employee == null || !passwordEncoder.matches(loginFormDTO.getPassword(), employee.getPassword())) {
            NotFoundException notFoundException = new NotFoundException("Pair login and password not found");
            log.error("Failed attempt to log in to account", notFoundException);
            throw notFoundException;
        }

        authenticate(employee.getId(), employee.getRole());
        return employee;
    }

    public void logoutUser() {
        getCurrentSession().invalidate();
    }

    public EmployeeEntity getUserBySession() {
        try {
            return
                    employeeEntityRepository.getById((Long) authentication().getCredentials());
        } catch (RuntimeException e) {
            return null;
        }
    }


}
