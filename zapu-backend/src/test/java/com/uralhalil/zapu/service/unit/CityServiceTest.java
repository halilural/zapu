package com.uralhalil.zapu.service.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.City;
import com.uralhalil.zapu.repository.CityRepository;
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

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "halil", password = "123456", roles = "USER")
class CityServiceTest {

    private static final String CITY_NAME_ISTANBUL = "İstanbul";
    private static final String CITY_NAME_ANKARA = "Ankara";
    private static final String CITY_NAME_IZMIR = "İzmir";
    private static final String CITY_NAME_NOT_FOUND = "NotFound";
    private static final String API_CITY_PATH = "/api/cities";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CityRepository cityRepository;

    City RECORD_1 = new City(UUID.randomUUID().toString(), CITY_NAME_ISTANBUL);
    City RECORD_2 = new City(UUID.randomUUID().toString(), CITY_NAME_ANKARA);
    City RECORD_3 = new City(UUID.randomUUID().toString(), CITY_NAME_IZMIR);


    @Test
    public void getAllRecords_success() throws Exception {
        List<City> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        Mockito.when(cityRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_CITY_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is(CITY_NAME_IZMIR)));
    }

    @Test
    public void getCityByName_success() throws Exception {
        Mockito.when(cityRepository.findByName(RECORD_1.getName())).thenReturn(java.util.Optional.of(RECORD_1));
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_CITY_PATH + "/" + CITY_NAME_ISTANBUL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(CITY_NAME_ISTANBUL)));
    }

    @Test
    public void createRecord_success() throws Exception {
        City record = City.builder()
                .id(UUID.randomUUID().toString())
                .name(CITY_NAME_ISTANBUL)
                .build();
        Mockito.when(cityRepository.save(record)).thenReturn(record);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(API_CITY_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(CITY_NAME_ISTANBUL)));
    }

//    @Test
//    public void updateCityRecord_success() throws Exception {
//        City updatedRecord = City.builder()
//                .name("Kitap")
//                .build();
//
//        Mockito.when(cityRepository.findById(RECORD_1.getName())).thenReturn(Optional.of(RECORD_1));
//        Mockito.when(cityRepository.save(updatedRecord)).thenReturn(updatedRecord);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(API_CITY_PATH)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(updatedRecord));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.name", is("Kitap")));
//    }
//
//    @Test
//    public void updateCityRecord_nullId() throws Exception {
//        CityRecord updatedRecord = CityRecord.builder()
//                .name("Sherlock Holmes")
//                .age(40)
//                .address("221B Baker Street")
//                .build();
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/city")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(updatedRecord));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof CityRecordController.InvalidRequestException))
//                .andExpect(result ->
//                        assertEquals("CityRecord or ID must not be null!", result.getResolvedException().getMessage()));
//    }
//
//    @Test
//    public void updateCityRecord_recordNotFound() throws Exception {
//        CityRecord updatedRecord = CityRecord.builder()
//                .cityId(5l)
//                .name("Sherlock Holmes")
//                .age(40)
//                .address("221B Baker Street")
//                .build();
//
//        Mockito.when(cityRecordRepository.findById(updatedRecord.getCityId())).thenReturn(null);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/city")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(updatedRecord));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof NotFoundException))
//                .andExpect(result ->
//                        assertEquals("City with ID 5 does not exist.", result.getResolvedException().getMessage()));
//    }

    @Test
    public void deleteCityByName_success() throws Exception {
        Mockito.when(cityRepository.findByName(RECORD_2.getName())).thenReturn(Optional.of(RECORD_2));
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_CITY_PATH + "/" + CITY_NAME_ANKARA)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCityByName_notFound() throws Exception {
        Mockito.when(cityRepository.findByName(CITY_NAME_NOT_FOUND)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_CITY_PATH + "/" + CITY_NAME_NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result ->
                        assertEquals("City with Name NotFound does not exist.", result.getResolvedException().getMessage()));
    }

}