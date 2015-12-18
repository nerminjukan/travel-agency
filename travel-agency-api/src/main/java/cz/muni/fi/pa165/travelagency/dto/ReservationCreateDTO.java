package cz.muni.fi.pa165.travelagency.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotNull;

public class ReservationCreateDTO {
    @NotNull
    private Long tripId;
    
    private Set<Long> excursionsId = new HashSet<>();

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Set<Long> getExcursionsId() {
        return excursionsId;
    }

    public void setExcursionsId(Set<Long> excursionsId) {
        this.excursionsId = excursionsId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservationCreateDTO other = (ReservationCreateDTO) obj;
        if (!Objects.equals(this.tripId, other.tripId)) {
            return false;
        }
        if (!Objects.equals(this.excursionsId, other.excursionsId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservationCreateDTO{" + "tripId=" + tripId + ", excursionsId=" + excursionsId + '}';
    }
}
