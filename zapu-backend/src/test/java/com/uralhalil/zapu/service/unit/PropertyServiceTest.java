package com.uralhalil.zapu.service.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.Category;
import com.uralhalil.zapu.model.City;
import com.uralhalil.zapu.model.Currency;
import com.uralhalil.zapu.model.Property;
import com.uralhalil.zapu.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.*;

import static com.uralhalil.zapu.service.util.TestConstant.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "halil", password = "123456", roles = "USER")
class PropertyServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PropertyRepository propertyRepository;

    final Property RECORD_1 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_BINA, 1000.00, Currency.TL);
    final Property RECORD_2 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_APARTMAN, 1500.00, Currency.TL);
    final Property RECORD_3 = new Property(UUID.randomUUID().toString(), PROPERTY_NAME_GECEKONDU, 300.00, Currency.TL);

    @BeforeEach
    private void setup() {
        Category category = new Category(UUID.randomUUID().toString(), CATEGORY_NAME_BOOK);
        City city = new City(UUID.randomUUID().toString(), CITY_NAME_IZMIR);
        RECORD_1.setCategory(category.getId());
        RECORD_1.setCity(city.getId());
        RECORD_2.setCategory(category.getId());
        RECORD_2.setCity(city.getId());
        RECORD_3.setCategory(category.getId());
        RECORD_3.setCity(city.getId());
    }

    @Test
    public void getAllRecords_success() throws Exception {
        List<Property> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        Mockito.when(propertyRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_PROPERTIES_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].title", is(PROPERTY_NAME_GECEKONDU)));
    }

    @Test
    public void getPropertyById_success() throws Exception {
        Mockito.when(propertyRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_PROPERTIES_PATH + "/" + RECORD_1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(RECORD_1.getId())));
    }

    @Test
    public void createRecord_success() throws Exception {

        Mockito.when(propertyRepository.save(RECORD_1)).thenReturn(RECORD_1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(API_PROPERTIES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(RECORD_1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(PROPERTY_NAME_BINA)));
    }

    @Test
    public void updateRecord_success() throws Exception {
        Mockito.when(propertyRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
        Mockito.when(propertyRepository.save(RECORD_1)).thenReturn(RECORD_1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(API_PROPERTIES_PATH + "/" + "{id}", RECORD_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(RECORD_1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(PROPERTY_NAME_BINA)));
    }

    @Test
    public void updateRecord_nullId() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(API_PROPERTIES_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(RECORD_1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isMethodNotAllowed())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(result ->
                        assertEquals("Request method 'PUT' not supported", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateRecord_recordNotFound() throws Exception {
        Mockito.when(propertyRepository.findById(RECORD_1.getId())).thenReturn(null);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(API_PROPERTIES_PATH + "/" + "{id}", RECORD_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(RECORD_1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result ->
                        assertEquals("Property with id " + RECORD_1.getId() + " does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    public void deleteById_success() throws Exception {
        Mockito.when(propertyRepository.findById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_PROPERTIES_PATH + "/" + RECORD_2.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById_notFound() throws Exception {
        Mockito.when(propertyRepository.findById(RECORD_3.getId())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_PROPERTIES_PATH + "/" + RECORD_3.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result ->
                        assertEquals("Property with id " + RECORD_3.getId() + " does not exist.", result.getResolvedException().getMessage()));
    }

}