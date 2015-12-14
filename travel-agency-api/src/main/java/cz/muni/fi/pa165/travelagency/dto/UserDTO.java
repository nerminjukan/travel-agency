package cz.muni.fi.pa165.travelagency.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * general DTO for User entity
 * 
 * @author Radovan Sinko
 */
public class UserDTO {
    
    @NotNull
    private Long id;
    
    @NotNull
    private String name;

    @NotNull
    private String email;
    
    @NotNull
    private String phoneNumber;   
    
    public Long getId() {
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

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
        if (!(obj instanceof UserDTO)) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        if (!Objects.equals(this.getEmail(), other.getEmail())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + '}';
    }
}
