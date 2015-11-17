package cz.muni.fi.pa165.travelagency.dto;

import cz.muni.fi.pa165.travelagency.utils.validation.DateRange;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Creation DTO for {@link cz.muni.fi.pa165.travelagency.entity.Excursion} entity
 *
 * @author omular
 */
@DateRange(start="dateFrom", end="dateTo")
public class ExcursionCreateDTO {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Date dateTo;

    @NotNull
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
                ", description=" + description +
                ", dateTo=" + dateTo +
                ", dateFrom=" + dateFrom +
                ", price=" + price +
                ", tripId=" + tripId +
                '}';
    }
}
