package com.ynding.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.ServerRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@ApiModel(value = "Menu", description = "菜单")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @ApiModelProperty(name = "id", notes = "ID", dataType = "long")
    private long id;

    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private Integer keepAlive;
    private Integer requireAuth;
    private Integer parentId;
    private Integer enabled;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id",scope = ServerRequest.class)
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "menu_role",
            joinColumns = @JoinColumn(name = "mid"),
            inverseJoinColumns = @JoinColumn(name = "rid"))
    private List<Role> roles;
}
