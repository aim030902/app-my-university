package com.company.appmyalllearnsproject.entity;

import com.company.appmyalllearnsproject.entity.enums.Permission;
import com.company.appmyalllearnsproject.entity.template.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "roles")
@EntityListeners(value = AuditingEntityListener.class)
public class Role extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Permission> permissionList;

    @Column(length = 600)
    private String description;
}
