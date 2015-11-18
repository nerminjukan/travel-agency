package cz.muni.fi.pa165.travelagency.entity;

import cz.muni.fi.pa165.travelagency.utils.validation.DateRange;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

/**
 * Entity Excursion represents optional part of the trip that can be reserved with Reservation.
 * Customer may have reserved multiple Excursions in one Trip.
 * @author Ondrej Glasnak
 * date    28.10.2015
 */
@Entity
@DateRange(start="dateFrom", end="dateTo")
public class Excursion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    private BigDecimal price;

    /**
     * constructor, assigns specified id.
     * @param id entity ID
     */
    public Excursion(Long id) {
        this.id = id;
    }

    /**
     * default constructor
     */
    public Excursion() {}

    /**
     * gets ID of excursion
     * @return id of excursion
     */
    public Long getId() {
        return id;
    }

    /**
     * sets ID of excursion
     * @param id ID of excursion
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * gets the Name of excursion
     * @return name of excursion
     */
    public String getName() {
        return name;
    }

    /**
     * sets specified name of the excursion
     * @param name Name of excursion
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the description of this excursion
     * @return description of excursion
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description for excursion
     * @param description Description of excursion
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the date on which excursion starts
     * @return date of the start of excursion
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * sets the starting date of excursion.
     * @param dateFrom date of the start of excursion
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * gets the date on which excursion ends.
     * @return date of the end of excursion
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * sets date of end of the excursion
     * @param dateTo end date of excursion
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * gets the destination of the excursion
     * @return destination of the excursion
     */
    public String getDestination() {
        return destination;
    }

    /**
     * sets destination of the excursion
     * @param destination destination of the excursion
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * gets price of the excursion
     * @return price of excursion
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * sets the price of the excursion
     * @param price price of the excursion
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * converts the information about the excursion into String form
     * @return string form of info about this excursion including all attributes.
     */
    @Override
    public String toString() {
        return "Excursion{" + "id=" + id + ", name=" + name + ", description=" +
                description + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo +
                ", destination=" + destination + ", price=" + price + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getName());
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
        if (!(obj instanceof Excursion)) {
            return false;
        }
        final Excursion other = (Excursion) obj;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        return true;
    }
}