package ru.dromran.testtz.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SessionUserDTO extends User {
    private final String userId;

    public SessionUserDTO(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
        this.userId = username;
    }

    public SessionUserDTO(String username,
                          boolean enabled,
                          boolean accountNonExpired,
                          boolean credentialsNonExpired,
                          boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = username;
    }
}
