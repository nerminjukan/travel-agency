package cz.muni.fi.pa165.travelagency.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jan Duda
 */
@Entity
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @NotNull
    @ManyToOne
    private Customer customer;
    
    @NotNull
    @ManyToOne
    private Trip trip;
    
    @ManyToMany
    private Set<Excursion> excursions = new HashSet<>();

    /**
     * Nonparametric constructor
     */
    public Reservation() {
    }

    /**
     * Constructor sets reservation with input id
     * 
     * @param Id new reservation will be set with this id
     */
    public Reservation(Long Id) {
        this.Id = Id;
    }

    /**
     * Method returns id of the reservation
     * 
     * @return id of the reservation
     */
    public Long getId() {
        return Id;
    }

    /**
     * Method sets id of the reservation
     * 
     * @param Id id of the reservation
     */
    public void setId(Long Id) {
        this.Id = Id;
    }

    /**
     * Method returns customer of the reservation
     * 
     * @return customer of the reservation
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Method sets customer of the reservation
     * 
     * @param customer customer of the reservation
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Method returns trip of the reservation
     * 
     * @return trip of the reservation
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Method sets trip of the reservation
     * 
     * @param trip trip of the reservation
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    /**
     * Method returns all excursions of the reservation
     * 
     * @return all excursions of the reservation
     */
    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    /**
     * Method adds excursion for the reservation
     * 
     * @param e excursion to add
     */
    public void addExcursion(Excursion e){
        excursions.add(e);
    }
    
    /**
     * Method removes excurcion of the reservation
     * 
     * @param e excursion to remove
     */
    public void removeExcursion(Excursion e){
        excursions.remove(e);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.customer);
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
        if (!(obj instanceof Reservation)) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.getCustomer(), other.getCustomer())) {
            return false;
        }
        if (!Objects.equals(this.getTrip(), other.getTrip())) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Reservation{" + "Id=" + Id + ", customer=" + customer + ", trip=" + trip + ", excursions=" + excursions + '}';
    }
    
    
}
