package cz.muni.fi.pa165.travelagency.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Creation DTO for Trip entity
 *
 * @author Jan Duda
 */
public class TripCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateFrom;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateTo;

    @NotNull
    @Size(min = 3, max = 50)
    private String destination;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Long availableTrips;

    /**
     * getter for Name
     * 
     * @return trip's name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for Name
     * 
     * @param name new trip's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for description.
     * 
     * @return Description or empty string if null.
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description
     * 
     * @param description new trip's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for start date of trip
     * 
     * @return trip's dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * setter for start date of trip
     * 
     * @param dateFrom new trip's dateFrom
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * getter for ending date of trip
     * 
     * @return trip's dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * setter for ending date of trip
     *
     * @param dateTo new trip's dateTo
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * getter for Destination
     * 
     * @return trip's destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * setter for Destination
     * 
     * @param destination new trip's destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * getter for price of the trip
     * 
     * @return trip's price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * setter for price of the trip
     * 
     * @param price new trip's price. Must be non-negative.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * gets number of total available trips.
     * 
     * @return Number of total available trips.
     */
    public Long getAvailableTrips() {
        return availableTrips;
    }

    /**
     * sets number of available trips.
     * 
     * @param availableTrips Number of available trips to be set. Minimum is 1
     */
    public void setAvailableTrips(Long availableTrips) {
        this.availableTrips = availableTrips;
    }

    @Override
    public String toString() {
        return "Trip{" + "Name=" + name
                + ", description=" + description
                + ", dateFrom=" + dateFrom
                + ", dateTo=" + dateTo
                + ", destination=" + destination
                + ", price=" + price
                + ", availableTrips=" + availableTrips + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        final TripCreateDTO other = (TripCreateDTO) o;
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