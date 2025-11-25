package com.raed.baffounexpress.user.DTO;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.raed.baffounexpress.user.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FullUser implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String country;
    private String city;
    private String address;
    private String zipCode;
    private String phone;
    private String photo;
    private boolean enabled;
    private boolean accountNonLocked;
    private boolean accountBanned;
    private GrantedAuthority authority;

    public static FullUser build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return new FullUser(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            user.getCountry(),
            user.getCity(),
            user.getAddress(),
            user.getZipCode(),
            user.getPhone(),
            user.getPhoto(),
            user.isEnabled(),
            user.isAccountNonLocked(),
            user.isAccountBanned(),
            authority
        );


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
