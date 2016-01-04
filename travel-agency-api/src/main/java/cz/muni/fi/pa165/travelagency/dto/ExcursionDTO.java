package cz.muni.fi.pa165.travelagency.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * General DTO for {@link cz.muni.fi.pa165.travelagency.entity.Excursion} entity
 *
 * @author omular
 */
public class ExcursionDTO {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private String description;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateFrom;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateTo;
    
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    @Size(min = 3, max = 50)
    private String destination;
    
    public ExcursionDTO(){
        
    }
    
    public ExcursionDTO(ExcursionDTO ex){
        id = ex.getId();
        name = ex.getName();
        description = ex.getDescription();
        destination = ex.getDestination();
        dateFrom = ex.getDateFrom();
        dateTo = ex.getDateTo();
        price = ex.getPrice();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final ExcursionDTO other = (ExcursionDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExcursionDTO{" +
                "id=" + id +
                ", name=" + name +
                ", destination=" + destination +
                ", description=" + description +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", price=" + price +
                '}';
    }
}
