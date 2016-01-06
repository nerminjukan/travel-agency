package cz.muni.fi.pa165.travelagency.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Update DTO for Excursion entity
 * 
 * @author Jan Duda
 */
public class ExcursionUpdateDTO extends ExcursionDTO {

    @NotNull
    private Long tripId;
    
    public ExcursionUpdateDTO(){
        
    }
    
    public ExcursionUpdateDTO(ExcursionDTO ex, Long tripId){
        super(ex);
        this.tripId = tripId;
    }
    
    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(super.getName());
        hash = 79 * hash + Objects.hashCode(this.tripId);
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
        final ExcursionUpdateDTO other = (ExcursionUpdateDTO) obj;
        if (!Objects.equals(super.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.tripId, other.tripId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExcursionCreateDTO{" +
                "id=" + super.getId() +
                ", name=" + super.getName() +
                ", destination=" + super.getDestination() +
                ", description=" + super.getDescription() +
                ", dateTo=" + super.getDateFrom() +
                ", dateFrom=" + super.getDateTo() +
                ", price=" + super.getPrice() +
                ", tripId=" + tripId +
                '}';
    }
}
