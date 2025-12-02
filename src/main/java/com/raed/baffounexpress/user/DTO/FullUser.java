package com.raed.baffounexpress.user.DTO;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raed.baffounexpress.user.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FullUser implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String country;
    private String city;
    private String address;
    private String zipCode;
    private String phone;
    private String photo;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private boolean accountNonLocked;
    @JsonIgnore
    private boolean accountBanned;
    @JsonIgnore
    private GrantedAuthority authority;

    public static FullUser construct(User user) {
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
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }

    @Override
    @JsonIgnore
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }

}
