package cz.muni.fi.pa165.travelagency.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Radovan Sinko
 */
@Entity
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String name;

    @NotNull
    @Column(unique=true)
    private String email;
    
    @NotNull
    private String phoneNumber;

    @NotNull
    private boolean isAdmin;

    @NotNull
    private String passwordHash;

    /**
     * nonparametric constructor
     */
    public User() {
    }

    /**
     * constructor of user which sets id.
     * @param id Id of entity
     */
    public User(Long id) {
        this.id = id;
    }

    /**
     * Determine if user is admin
     * @return true if user is admin, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * set admin permissions for user
     * @param isAdmin new user permission
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * get hash of user's passwords
     * @return hash of user's password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * set hash of user's password
     * @param passwordHash new password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    /**
     * gets ID of user
     * @return id Id of user
     */
    public Long getId() {
        return id;
    }

    /**
     * gets the Name of user
     * @return name Name of user
     */
    public String getName() {
        return name;
    }

    /**
     * sets specified name of the user
     * @param name Name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the email of user
     * @return email Email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets specified email of the user
     * @param email Email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the phone number of user
     * @return phoneNumber Phone number of user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets specified phone number of the user
     * @param phoneNumber Phone number of user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.getEmail());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.getEmail(), other.getEmail())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", isAdmin= " + isAdmin + '}';
    }
       
    
}
