package pw.cicek.spring_tutorial.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {
    private String authority;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "ID")
    )
    private Set<Privilege> privileges = new HashSet<>();

    public Role() {
        super();
    }

    public Role(String authority) {
        super();

        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = new HashSet<>(privileges);
    }

    public void addPrivilege(Privilege privilege) {
        if(!privileges.contains(privilege))
            this.privileges.add(privilege);
    }

    public void addPrivilege(Collection<Privilege> privileges) {
        privileges.forEach(this::addPrivilege);
    }
    @Override
    public String toString() {
        return "Role{" +
                "authority='" + authority + '\'' +
                ", privileges=" + privileges +
                '}';
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
