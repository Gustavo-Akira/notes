package br.com.gustavoakira.agenda.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
public class Users extends RepresentationModel<Users> implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Email
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "roles_users")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Role> roles = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
