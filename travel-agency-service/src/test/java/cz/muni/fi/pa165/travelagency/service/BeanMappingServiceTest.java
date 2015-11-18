/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.entity.Excursion;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfiguration;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author omular
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BeanMappingService beanMappingService;

    private Excursion excursion1;
    private Excursion excursion2;
    private ExcursionDTO excursionDTO1;
    private ExcursionDTO excursionDTO2;
    private List<Excursion> excursionsList;
    private List<ExcursionDTO> excursionsDTOList;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        excursion1 = new Excursion();
        excursion1.setId(new Long("1"));
        excursion1.setName("Name");
        excursion1.setDescription("Description");
        excursion1.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion1.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursion1.setDestination("Germany");
        excursion1.setPrice(new BigDecimal("100.0"));

        excursion2 = new Excursion();
        excursion2.setId(new Long("2"));
        excursion2.setName("Name1");
        excursion2.setDescription("Description1");
        excursion2.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion2.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursion2.setDestination("Japan");
        excursion2.setPrice(new BigDecimal("200.0"));

        excursionsList = new ArrayList<>();
        excursionsList.add(excursion1);
        excursionsList.add(excursion2);

        excursionDTO1 = new ExcursionDTO();
        excursionDTO1.setId(new Long("1"));
        excursionDTO1.setName("Name");
        excursionDTO1.setDescription("Description");
        excursionDTO1.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursionDTO1.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursionDTO1.setDestination("Germany");
        excursionDTO1.setPrice(new BigDecimal("100.0"));

        excursionDTO2 = new ExcursionDTO();
        excursionDTO2.setId(new Long("2"));
        excursionDTO2.setName("Name1");
        excursionDTO2.setDescription("Description1");
        excursionDTO2.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursionDTO2.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1)));
        excursionDTO2.setDestination("Japan");
        excursionDTO2.setPrice(new BigDecimal("200.0"));

        excursionsDTOList = new ArrayList<>();
        excursionsDTOList.add(excursionDTO1);
        excursionsDTOList.add(excursionDTO2);
    }

    @Test
    public void testMapToCollectionExcursionDTOToEntity() {
        List<Excursion> eList = beanMappingService.mapTo(
                excursionsDTOList, Excursion.class
        );
        assertDeepEquals(eList.get(0), excursion1);
        assertDeepEquals(eList.get(1), excursion2);
    }

    @Test
    public void testMapToCollectionExcursionEntityToDTO() {
        List<ExcursionDTO> eList = beanMappingService.mapTo(
                excursionsList, ExcursionDTO.class
        );
        assertDeepEquals(eList.get(0), excursionDTO1);
        assertDeepEquals(eList.get(1), excursionDTO2);
    }

    @Test
    public void testMapToExcursionDTOToEntity() {
        Excursion e = beanMappingService.mapTo(excursionDTO1, Excursion.class);
        assertDeepEquals(excursion1, e);
    }

    @Test
    public void testMapToExcursionEntityToDTO() {
        ExcursionDTO e = beanMappingService.mapTo(excursion1, ExcursionDTO.class);
        assertDeepEquals(excursionDTO1, e);
    }

    private void assertDeepEquals(Excursion excursion, Excursion excursion1) {
        assertEquals(excursion, excursion1);
        assertEquals(excursion.getId(), excursion1.getId());
        assertEquals(excursion.getName(), excursion1.getName());
        assertEquals(excursion.getDescription(), excursion1.getDescription());
        assertEquals(excursion.getDateFrom(), excursion1.getDateFrom());
        assertEquals(excursion.getDateTo(), excursion1.getDateTo());
        assertEquals(excursion.getDestination(), excursion1.getDestination());
        assertEquals(excursion.getPrice(), excursion1.getPrice());
    }

    private void assertDeepEquals(ExcursionDTO excursion, ExcursionDTO excursion1) {
        assertEquals(excursion, excursion1);
        assertEquals(excursion.getId(), excursion1.getId());
        assertEquals(excursion.getName(), excursion1.getName());
        assertEquals(excursion.getDescription(), excursion1.getDescription());
        assertEquals(excursion.getDateFrom(), excursion1.getDateFrom());
        assertEquals(excursion.getDateTo(), excursion1.getDateTo());
        assertEquals(excursion.getDestination(), excursion1.getDestination());
        assertEquals(excursion.getPrice(), excursion1.getPrice());
    }
}
