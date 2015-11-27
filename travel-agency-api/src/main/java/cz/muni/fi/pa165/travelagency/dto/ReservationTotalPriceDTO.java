/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author omular
 */
public class ReservationTotalPriceDTO {
    private ReservationDTO reservation;

    private BigDecimal price;

    public ReservationTotalPriceDTO() {
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.reservation);
        hash = 37 * hash + Objects.hashCode(this.price);
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
        final ReservationTotalPriceDTO other = (ReservationTotalPriceDTO) obj;
        if (!Objects.equals(this.reservation, other.reservation)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservationTotalPriceDTO{" + "reservation=" + reservation + ", price=" + price + '}';
    }
}
