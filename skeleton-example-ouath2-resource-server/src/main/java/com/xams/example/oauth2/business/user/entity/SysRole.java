package com.xams.example.oauth2.business.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
public class SysRole implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 9207553824303943745L;

    private Integer id;
    private String roleName;
    private String roleDesc;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return roleName;
    }
}
