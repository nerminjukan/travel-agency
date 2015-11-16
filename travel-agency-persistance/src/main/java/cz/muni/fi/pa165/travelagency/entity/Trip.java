package cz.muni.fi.pa165.travelagency.entity;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Ondrej Mular
 */
@Entity
public class Trip {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(unique=true)
    private String name;
    
    private String description;
    
    @NotNull
    private Date dateFrom;
    
    @NotNull
    private Date dateTo;
    
    @NotNull
    private String destination;
    
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;
    
    @OneToMany
    private Set<Excursion> excursions = new HashSet<>();
    
    @NotNull
    @Min(1)
    private Long availibleTrips;

    public Trip(Long id) {
        this.id = id;
    }

    public Trip() {
    }

    public Long getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
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

    public Long getAvailibleTrips() {
        return availibleTrips;
    }

    public void setAvailibleTrips(Long availibleTrips) {
        this.availibleTrips = availibleTrips;
    }
    
    public void addExcursion(Excursion e) {
        excursions.add(e);
    }
    
    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }
    
    public void removeExcursion(Excursion e) {
        excursions.remove(e);
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id + ", name=" + name + ", description=" +
                description + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo +
                ", destination=" + destination + ", price=" + price +
                ", excursions=" + excursions + ", availibleTrips=" +
                availibleTrips + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getName());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Trip)) {
            return false;
        }
        final Trip other = (Trip) obj;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        return true;
    }
}
