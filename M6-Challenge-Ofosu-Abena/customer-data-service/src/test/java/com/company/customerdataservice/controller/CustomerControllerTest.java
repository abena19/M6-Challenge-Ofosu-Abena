package com.company.customerdataservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerRepository repo;

    private Customer customer1, customer2;

    @BeforeEach
    public void setup() {
        customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setState("California");

        customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
        customer2.setState("New York");
    }

    @Test
    public void getCustomersTest() throws Exception {
        Mockito.when(repo.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(customer1.getFirstName())))
                .andExpect(jsonPath("$[1].firstName", is(customer2.getFirstName())));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(customer1));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(customer1.getFirstName())));
    }

    @Test
    public void addCustomerTest() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Test");
        newCustomer.setLastName("User");
        newCustomer.setState("Texas");

        Mockito.when(repo.save(newCustomer)).thenReturn(newCustomer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(newCustomer.getFirstName())));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        customer1.setFirstName("UpdatedName");
        Mockito.when(repo.save(customer1)).thenReturn(customer1);

        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer1)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        Mockito.doNothing().when(repo).deleteById(1);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());
    }

    // Add other tests for the remaining controller methods
}
