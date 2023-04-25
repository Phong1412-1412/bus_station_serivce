package com.busstation.services.securityimpl;

import com.busstation.entities.Account;
import com.busstation.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserRegistrationDetails implements UserDetails {
    private String userName;
    private String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public UserRegistrationDetails(Account account, Boolean status) {
        this.userName = account.getUsername();
        this.password = account.getPassword();
        this.isEnabled = status;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().getName().trim()));
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
