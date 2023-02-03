package com.company.appmyalllearnsproject.entity;

import com.company.appmyalllearnsproject.entity.enums.Permission;
import com.company.appmyalllearnsproject.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
@EntityListeners(value = AuditingEntityListener.class)
public class User  extends AbstractEntity implements UserDetails {
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Role role;

    private String emailCode;

    private boolean enabled;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permission> permissionList = this.role.getPermissionList();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//      1 - USUL
//      for (Permission permission : permissionList) {
//         grantedAuthorities.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//               return permission.name();
//            }
//         });
//      }
//      2 - USUL
//      for (Permission permission : permissionList) {
//         grantedAuthorities.add((GrantedAuthority) permission::name);
//      }

        // 3 - USUL
        for (Permission permission : permissionList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.name()));
        }
        return grantedAuthorities;
    }

    public User(String fullName, String username, String password, Role role, String emailCode, boolean enabled) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.emailCode = emailCode;
        this.enabled = enabled;
    }
}
