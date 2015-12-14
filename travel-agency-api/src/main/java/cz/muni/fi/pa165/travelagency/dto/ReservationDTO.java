package cz.muni.fi.pa165.travelagency.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 * General DTO for {@link cz.muni.fi.pa165.travelagency.entity.Reservation} entity
 *
 * @author Jan Duda
 */
public class ReservationDTO {
    @NotNull
    private Long id;
    
    @NotNull
    private UserDTO user;
    
    @NotNull
    private TripDTO trip;
    
    private Set<ExcursionDTO> excursions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public Set<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(Set<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.user);
        hash = 89 * hash + Objects.hashCode(this.trip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ReservationDTO)) {
            return false;
        }
        final ReservationDTO other = (ReservationDTO) obj;
        if (!Objects.equals(this.getUser(), other.getUser())) {
            return false;
        }
        if (!Objects.equals(this.getTrip(), other.getTrip())) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Reservation{" + "Id=" + id + ", user=" + user + ", trip=" + trip + ", excursions=" + excursions + '}';
    }
}
