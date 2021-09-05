package com.uralhalil.zapu.service.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uralhalil.zapu.exception.NotFoundException;
import com.uralhalil.zapu.model.Category;
import com.uralhalil.zapu.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CategoryRepository categoryRepository;

    Category RECORD_1 = new Category(UUID.randomUUID().toString(), CATEGORY_NAME_BOOK);
    Category RECORD_2 = new Category(UUID.randomUUID().toString(), CATEGORY_NAME_NOTEBOOK);
    Category RECORD_3 = new Category(UUID.randomUUID().toString(), CATEGORY_NAME_ERASER);


    @Test
    public void getAllRecords_success() throws Exception {
        List<Category> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        Mockito.when(categoryRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_CATEGORY_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is(CATEGORY_NAME_ERASER)));
    }

    @Test
    public void getCategoryByName_success() throws Exception {
        Mockito.when(categoryRepository.findByName(RECORD_1.getName())).thenReturn(java.util.Optional.of(RECORD_1));
        mockMvc.perform(MockMvcRequestBuilders
                .get(API_CATEGORY_PATH + "/" + CATEGORY_NAME_BOOK)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(CATEGORY_NAME_BOOK)));
    }

    @Test
    public void createRecord_success() throws Exception {
        Category record = Category.builder()
                .id(UUID.randomUUID().toString())
                .name(CATEGORY_NAME_BOOK)
                .build();
        Mockito.when(categoryRepository.save(record)).thenReturn(record);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(API_CATEGORY_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(CATEGORY_NAME_BOOK)));
    }

//    @Test
//    public void updateCategoryRecord_success() throws Exception {
//        Category updatedRecord = Category.builder()
//                .name("Kitap")
//                .build();
//
//        Mockito.when(categoryRepository.findById(RECORD_1.getName())).thenReturn(Optional.of(RECORD_1));
//        Mockito.when(categoryRepository.save(updatedRecord)).thenReturn(updatedRecord);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(API_CATEGORY_PATH)
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
//    public void updateCategoryRecord_nullId() throws Exception {
//        CategoryRecord updatedRecord = CategoryRecord.builder()
//                .name("Sherlock Holmes")
//                .age(40)
//                .address("221B Baker Street")
//                .build();
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/category")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(updatedRecord));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof CategoryRecordController.InvalidRequestException))
//                .andExpect(result ->
//                        assertEquals("CategoryRecord or ID must not be null!", result.getResolvedException().getMessage()));
//    }
//
//    @Test
//    public void updateCategoryRecord_recordNotFound() throws Exception {
//        CategoryRecord updatedRecord = CategoryRecord.builder()
//                .categoryId(5l)
//                .name("Sherlock Holmes")
//                .age(40)
//                .address("221B Baker Street")
//                .build();
//
//        Mockito.when(categoryRecordRepository.findById(updatedRecord.getCategoryId())).thenReturn(null);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/category")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(updatedRecord));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isBadRequest())
//                .andExpect(result ->
//                        assertTrue(result.getResolvedException() instanceof NotFoundException))
//                .andExpect(result ->
//                        assertEquals("Category with ID 5 does not exist.", result.getResolvedException().getMessage()));
//    }

    @Test
    public void deleteCategoryByName_success() throws Exception {
        Mockito.when(categoryRepository.findByName(RECORD_2.getName())).thenReturn(Optional.of(RECORD_2));
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_CATEGORY_PATH + "/" + CATEGORY_NAME_NOTEBOOK)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCategoryByName_notFound() throws Exception {
        Mockito.when(categoryRepository.findByName(CATEGORY_NAME_NOT_FOUND)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .delete(API_CATEGORY_PATH + "/" + CATEGORY_NAME_NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result ->
                        assertEquals("Category with Name NotFound does not exist.", result.getResolvedException().getMessage()));
    }

}