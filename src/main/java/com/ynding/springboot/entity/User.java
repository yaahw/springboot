package com.ynding.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.ServerRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@ApiModel(value = "User", description = "用户")
public class User implements UserDetails,Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @ApiModelProperty(name = "id", notes = "ID", dataType = "long")
    private long id;
    private String name;
    private String phone;
    private String telephone;
    private String address;
    @NotNull
    private int enabled;
    private String username;
    private String password;
    private String remark;
    
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id",scope = ServerRequest.class)
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "userid"),
        inverseJoinColumns = @JoinColumn(name = "rid"))
    private List<Role> roles;
    private String userface;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户是否过期
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {//账户是否锁定
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {//密码是否过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }
}
