package cz.muni.fi.pa165.travelagency.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.travelagency.RootWebContext;
import cz.muni.fi.pa165.travelagency.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionDTO;
import cz.muni.fi.pa165.travelagency.dto.ExcursionUpdateDTO;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.rest.controllers.ExcursionsController;
import cz.muni.fi.pa165.travelagency.rest.controllers.GlobalExceptionController;
import java.io.IOException;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Duda
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class ExcursionControllerTest extends AbstractTestNGSpringContextTests {
    
    @Mock
    private ExcursionFacade excursionFacade;
    
    @Autowired
    @InjectMocks
    private ExcursionsController excursionsController;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(excursionsController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }
    
    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(GlobalExceptionController.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new GlobalExceptionController(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }
    
    @Test
    public void debugTest() throws Exception {
        doReturn(Collections.unmodifiableList(this.createExcursions())).when(
                excursionFacade).getAllExcursions();
        mockMvc.perform(get("/excursions")).andDo(print());
    }
    
    @Test
    public void testGetAllExcursions() throws Exception{
        doReturn(Collections.unmodifiableList(this.createExcursions())).when(
                excursionFacade).getAllExcursions();
        
        mockMvc.perform(get("/excursions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Excursion to Prague"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Excursion to Brno"));
    }
    
    @Test
    public void testGetValidExcursion() throws Exception{
        List<ExcursionDTO> excursions = this.createExcursions();
        
        doReturn(excursions.get(0)).when(excursionFacade).getExcursionById(1L);
        doReturn(excursions.get(1)).when(excursionFacade).getExcursionById(2L);
        
        mockMvc.perform(get("/excursions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Excursion to Prague"));
        mockMvc.perform(get("/excursions/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Excursion to Brno"));
    }
    
    @Test
    public void testGetInvalidExcursion() throws Exception {
        doThrow(new RuntimeException()).when(excursionFacade).getExcursionById(11L);
        
        mockMvc.perform(get("/excursions/11"))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testDeleteExcursion() throws Exception {
        List<ExcursionDTO> excursions = this.createExcursions();
        
        mockMvc.perform(delete("/excursions/1"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testDeleteNonExictingExcursion() throws Exception {
        List<ExcursionDTO> excursions = this.createExcursions();
        
        doThrow(new RuntimeException("The excursion does not exist")).when(excursionFacade).deleteExcursion(11L);
        mockMvc.perform(delete("/excursions/11"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testCreateExcursion() throws Exception {
        ExcursionCreateDTO excursionCreateDTO = new ExcursionCreateDTO();
        excursionCreateDTO.setName("Excursion to Prague");
        
        doReturn(1L).when(excursionFacade).createExcursion(any(ExcursionCreateDTO.class));
        
        String json = this.convertObjectToJsonBytes(excursionCreateDTO);
        
        mockMvc.perform(post("/excursions/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void testUpdateExcursion() throws Exception {
        List<ExcursionDTO> excursions = this.createExcursions();
        
        doReturn(excursions.get(0)).when(excursionFacade).getExcursionById(1L);
        doReturn(excursions.get(1)).when(excursionFacade).getExcursionById(2L);
        
        doNothing().when(excursionFacade).updateExcursion(any(ExcursionUpdateDTO.class));
        ExcursionUpdateDTO excursionToUpdate = new ExcursionUpdateDTO(excursions.get(0));
        excursionToUpdate.setName("Excursion to Ostrava");
        
        String json = this.convertObjectToJsonBytes(excursionToUpdate);
        
        mockMvc.perform(put("/excursions/update").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void testUpdateNonExistingExcursion() throws Exception {
        List<ExcursionDTO> excursions = this.createExcursions();
        
        doReturn(excursions.get(0)).when(excursionFacade).getExcursionById(1L);
        doReturn(excursions.get(1)).when(excursionFacade).getExcursionById(2L);
        
        ExcursionUpdateDTO excursionToUpdate = new ExcursionUpdateDTO();
        excursionToUpdate.setId(11L);
        excursionToUpdate.setName("Excursion to Ostrava");
        excursionToUpdate.setDestination("Ostrava");
        excursionToUpdate.setDescription("Great description");
        excursionToUpdate.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursionToUpdate.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1l)));
        excursionToUpdate.setPrice(new BigDecimal("50"));
        
        doThrow(new RuntimeException("The excursion does not exist")).when(excursionFacade).updateExcursion(excursionToUpdate);
        
        String json = this.convertObjectToJsonBytes(excursionToUpdate);
        
        mockMvc.perform(put("/excursions/update").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().is4xxClientError());
    }
    
    private List<ExcursionDTO> createExcursions(){
        ExcursionDTO excursion1 = new ExcursionDTO();
        excursion1.setId(1L);
        excursion1.setName("Excursion to Prague");
        excursion1.setDestination("Prague");
        excursion1.setDescription("Awesome excursion");
        excursion1.setDateFrom(Date.valueOf(LocalDate.of(2015, 1, 1)));
        excursion1.setDateTo(Date.valueOf(LocalDate.of(2015, 1, 1).plusDays(1l)));
        excursion1.setPrice(new BigDecimal("100"));
        
        ExcursionDTO excursion2 = new ExcursionDTO();
        excursion2.setId(2L);
        excursion2.setName("Excursion to Brno");
        excursion2.setDestination("Brno");
        excursion2.setDescription("Cool excursion");
        excursion2.setDateFrom(Date.valueOf(LocalDate.of(2015, 2, 1)));
        excursion2.setDateTo(Date.valueOf(LocalDate.of(2015, 2, 1).plusDays(1l)));
        excursion2.setPrice(new BigDecimal("80"));
        
        return Arrays.asList(excursion1, excursion2);
    }
    
    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
