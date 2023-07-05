package com.pifrans.ecommerce.securities;

import com.pifrans.ecommerce.domains.entities.Profile;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserDetailSecurity implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailSecurity(Long id, String email, String password, Set<Profile> profiles) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = profiles.stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList());
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
        return email;
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
        return true;
    }

    public Long getId() {
        return id;
    }

    public boolean hasRole(Profile profile) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getName()));
    }
}
