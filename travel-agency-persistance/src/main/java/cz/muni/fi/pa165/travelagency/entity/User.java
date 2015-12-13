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

    private boolean admin;

    @NotNull
    private String passwordHash;

    /**
     * nonparametric constructor
     */
    public User() {
    }

    /**
     * constructor of customer which sets id.
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
        return admin;
    }

    /**
     * set admin permissions for user
     * @param isAdmin new user permission
     */
    public void setIsAdmin(boolean isAdmin) {
        this.admin = isAdmin;
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
     * gets ID of customer
     * @return id Id of customer
     */
    public Long getId() {
        return id;
    }

    /**
     * gets the Name of customer
     * @return name Name of customer
     */
    public String getName() {
        return name;
    }

    /**
     * sets specified name of the customer
     * @param name Name of customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the email of customer
     * @return email Email of customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets specified email of the customer
     * @param email Email of customer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the phone number of customer
     * @return phoneNumber Phone number of customer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets specified phone number of the customer
     * @param phoneNumber Phone number of customer
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
        return "Customer{" + "id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + '}';
    }
       
    
}
