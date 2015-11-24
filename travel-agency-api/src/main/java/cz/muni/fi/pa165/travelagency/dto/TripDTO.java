package cz.muni.fi.pa165.travelagency.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * general DTO for handling information about Trip entity.
 *
 * @author Ondrej Glasnak
 * date 22/11/15
 */
public class TripDTO {
    private Long id;

    @NotNull
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

    private Set<ExcursionDTO> excursions = new HashSet<>();

    @NotNull
    @Min(1)
    private Long availableTrips;

    /**
     * getter for ID
     */
    public Long getId() {
        return id;
    }

    /**
     * setter for ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for Name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for description.
     * @return Description or empty string if null.
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for start date of trip
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * setter for start date of trip
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * getter for ending date of trip
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * setter for ending date of trip
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * getter for Destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * setter for Destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * getter for price of the trip
     * @return Price of trip.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter for price of the trip.
     * @param price Price of the trip. Must be non-negative.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<ExcursionDTO> getExcursions() {
        return excursions;
    }

    /**
     * sets list of excursions. (addExcursion and removeExcursion might be added for finer control)
     * @param excursions set of excursions belonging to the trip.
     */
    public void setExcursions(Set<ExcursionDTO> excursions) {
        this.excursions = excursions;
    }

    /**
     * gets number of total available trips.
     * @return Number of total available trips.
     */
    public Long getAvailableTrips() {
        return availableTrips;
    }

    /**
     * sets number of available trips.
     * @param availableTrips Number of available trips to be set. Minimum is 1
     */
    public void setAvailableTrips(Long availableTrips) {
        this.availableTrips = availableTrips;
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id
                + ", name=" + name
                + ", description=" + description
                + ", dateFrom=" + dateFrom
                + ", dateTo=" + dateTo
                + ", destination=" + destination
                + ", price=" + price
                + ", excursions=" + excursions
                + ", availableTrips=" + availableTrips + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        final TripDTO other = (TripDTO) o;
        if (!Objects.equals(this.getName(), other.getName()))
            return false;

        return true;

    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 47 * hash + Objects.hashCode(this.getName());
        return hash;
    }
}
