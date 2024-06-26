package com.eliu.tourist.agency.touristagency.models;

import java.util.ArrayList;
import java.util.List;

import com.eliu.tourist.agency.touristagency.validations.IsRequired;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    @IsRequired
    @Size(min=4, max=20)
    private String username;

    @JsonProperty(access=Access.WRITE_ONLY)
    @IsRequired
    @Size(min=5, max=25)
    private String password;

    private String token;

    private Boolean isEnabled;

    // Los atributos con la anotación @Transient, son propios de la app, por lo tanto,
    // no se guardan en la BD, y su propósito es para asignar los roles al usuario.
    @Transient
    @JsonProperty(access=Access.WRITE_ONLY)
    @NotNull
    private Boolean isClient;
    
    @Transient
    @JsonProperty(access=Access.WRITE_ONLY)
    @NotNull
    private Boolean isAdmin;

    @OneToOne
    @JoinColumn(name="person_id")
    @JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
    private Person person;

    @ManyToMany
    @JoinTable( name="users_roles",
                joinColumns=@JoinColumn(name="user_id"),
                inverseJoinColumns=@JoinColumn(name="role_id"),
                uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "role_id"}))
    @JsonIgnoreProperties({"users", "hibernateLazyInitializer", "handler"})                
    private List<Role> roles;

    public User() {
        roles = new ArrayList<>();
    }

    public User(@Size(min = 4, max = 20) String username, @Size(min = 5, max = 25) String password, String token,
            Boolean isEnabled, @NotNull Boolean isClient, @NotNull Boolean isAdmin, Person person, List<Role> roles) {
        this();
        this.username = username;
        this.password = password;
        this.token = token;
        this.isEnabled = isEnabled;
        this.isClient = isClient;
        this.isAdmin = isAdmin;
        this.person = person;
        this.roles = roles;
    }

    @PrePersist
    public void prePersist() {
        this.isEnabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsClient() {
        return isClient;
    }

    public void setIsClient(Boolean isClient) {
        this.isClient = isClient;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", username=" + username +
                ", isEnabled=" + isEnabled +
                ", person=" + person +
                ", roles=" + roles + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((isEnabled == null) ? 0 : isEnabled.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        if (isEnabled == null) {
            if (other.isEnabled != null)
                return false;
        } else if (!isEnabled.equals(other.isEnabled))
            return false;
        return true;
    }

}
