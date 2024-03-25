package com.andretask.salesmanagement.controller;

import com.andretask.salesmanagement.controllers.SaleController;
import com.andretask.salesmanagement.controllers.dto.*;
import com.andretask.salesmanagement.services.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebMvcTest(SaleController.class)
public class SaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaleService saleService;

    @Test
    public void testFetchSales() throws Exception {

        List<SaleResponseDto> sales = Arrays.asList(new SaleResponseDto(1L, new Date(), 1L, 2L, 100.0));
        Mockito.when(saleService.fetchSales()).thenReturn(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/sales"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].total", Matchers.is(100.0)));
    }

    @Test
    public void testCreateSale() throws Exception {
        List<TransactionDto> transactions = Arrays.asList(new TransactionDto(1L, 5, 20.0));
        SaleCreateDto createDto = new SaleCreateDto(1L, 2L, transactions);
        SaleResponseDto responseDto = new SaleResponseDto(2L, new Date(), 1L, 2L, 200.0);

        Mockito.when(saleService.createSale(Mockito.any(SaleCreateDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is(200.0)));
    }

    @Test
    public void testUpdateSale() throws Exception {
        List<TransactionUpdateDto> updatedTransactions = Arrays.asList(new TransactionUpdateDto(1L, 10, 25.0));
        SaleUpdateDto updateDto = new SaleUpdateDto(updatedTransactions);
        SaleResponseDto responseDto = new SaleResponseDto(1L, new Date(), 1L, 2L, 250.0);

        Mockito.when(saleService.updateSale(Mockito.eq(1L), Mockito.any(SaleUpdateDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/sales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is(250.0)));
    }

}
