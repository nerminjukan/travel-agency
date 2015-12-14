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
 * Entity class which represents trip in Travel Agency. Excursions
 * are optional parts of Trip and User can make reservation for them.
 * It is possible to add multiple {@link cz.muni.fi.pa165.travelagency.entity.Excursion Excursion} to Trip.
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
    private Long availableTrips;

    /**
     * Constructor of Trip which sets id.
     *
     * @param id Id of entity
     */
    public Trip(Long id) {
        this.id = id;
    }

    /**
     * Nonparametric constructor.
     */
    public Trip() {
    }

    /**
     * Method returns id of trip.
     *
     * @return id of trip
     */
    public Long getId() {
        return id;
    }

    /**
     * Method sets id of trip.
     *
     * @param id Id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method returns name of trip.
     *
     * @return Name of trip.
     */
    public String getName() {
        return name;
    }

    /**
     * Method sets name of trip.
     *
     * @param name Name to be set. Name of trips has to be unique.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method returns description of trip.
     *
     * @return Description of trip.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method sets description of trip.
     *
     * @param description Description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method returns star date of trip.
     *
     * @return Start date of trip.
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * Method sets start date of trip
     *
     * @param dateFrom Date to be set as start date of trip.
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Method returns end date of trip
     *
     * @return End date of trip.
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * Method sets end date of trip.
     *
     * @param dateTo Date to be set as end date of trip.
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Method returns destination of trip.
     *
     * @return Destination of trip.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Method sets destination of trip.
     *
     * @param destination Destination to be set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Method returns price of Trip.
     *
     * @return Price of trip.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Method sets price of trip.
     *
     * @param price Price to be set. Price has to be nonnegative decimal
     * number.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Method return number of total available trips.
     *
     * @return Number of total available trips.
     */
    public Long getAvailableTrips() {
        return availableTrips;
    }

    /**
     * Method sets number of available trips.
     *
     * @param availableTrips Number of available trips to be set.
     */
    public void setAvailableTrips(Long availableTrips) {
        this.availableTrips = availableTrips;
    }

    /**
     * Adds excursion to trip.
     *
     * @param e Excursion to add.
     */
    public void addExcursion(Excursion e) {
        excursions.add(e);
    }

    /**
     * Returns set of Excursion assigned to trip.
     *
     * @return Set of Excursion assigned to trip.
     */
    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    /**
     * Removes excursion from trip.
     *
     * @param e Excursion to be removed.
     */
    public void removeExcursion(Excursion e) {
        excursions.remove(e);
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id + ", name=" + name + ", description=" +
                description + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo +
                ", destination=" + destination + ", price=" + price +
                ", excursions=" + excursions + ", availableTrips=" +
                availableTrips + '}';
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
