package server.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends DefaultEntity implements GrantedAuthority {
    @NaturalId
    @Enumerated(EnumType.STRING)
    private Privilege privilege;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String getAuthority() {
        return privilege.name();
    }

    @AllArgsConstructor
    @Getter
    public enum Privilege {
        ROLE_USER, ROLE_MANAGER, ROLE_ADMIN;
    }
}
