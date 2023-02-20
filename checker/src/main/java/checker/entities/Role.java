package checker.entities;

import checker.enums.RoleType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(columnDefinition = "ENUM('ADMIN', 'PROFESSOR','STUDENT')")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToMany(mappedBy = "role")
    private Set<User> users=new HashSet<>();

    public Role() {}

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public Long getRoleId() {
        return roleId;
    }


}
