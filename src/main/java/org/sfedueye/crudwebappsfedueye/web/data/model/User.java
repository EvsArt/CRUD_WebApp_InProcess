package org.sfedueye.crudwebappsfedueye.web.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usver")  // Because "user" is busy by Postgres
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    public User(String email, String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usver_generator")
    @SequenceGenerator(name = "usver_generator", sequenceName = "usver_seq", allocationSize = 1)
    private long id;

    @Size(max = 500, message = "Длина поля не более 500 символов!")
    @Pattern(regexp = "^[a-z]+@sfedu\\.ru$", message = "Введите почту @sfedu.ru")
    private String email;

    private String password;

    @ManyToOne
    private Role role;

    @OneToOne(cascade=CascadeType.ALL)
    private UserInfo userInfo = new UserInfo();

    private boolean isDeleted;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));
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
        return !isDeleted;
    }
}
