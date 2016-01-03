package cz.muni.fi.pa165.travelagency.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Creation DTO for {@link cz.muni.fi.pa165.travelagency.entity.Excursion} entity
 *
 * @author omular
 */
public class ExcursionCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    
    @NotNull
    @Size(min = 3, max = 50)
    private String destination;

    @Size(max = 500)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateTo;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateFrom;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    private Long tripId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final ExcursionCreateDTO other = (ExcursionCreateDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
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
                "name=" + name +
                ", destination=" + destination +
                ", description=" + description +
                ", dateTo=" + dateTo +
                ", dateFrom=" + dateFrom +
                ", price=" + price +
                ", tripId=" + tripId +
                '}';
    }
}
