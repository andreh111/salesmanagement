package com.andretask.salesmanagement.controller;

import com.andretask.salesmanagement.controllers.ClientController;
import com.andretask.salesmanagement.controllers.dto.ClientCreateDto;
import com.andretask.salesmanagement.controllers.dto.ClientResponseDto;
import com.andretask.salesmanagement.controllers.dto.ClientUpdateDto;
import com.andretask.salesmanagement.models.Client;
import com.andretask.salesmanagement.services.ClientService;
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
import java.util.List;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void testFetchClients() throws Exception {
        List<Client> clients = Arrays.asList(new Client(1L, "John", "Doe", "1234567890"));
        Mockito.when(clientService.fetchClients()).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("John")));
    }

    @Test
    public void testCreateClient() throws Exception {
        ClientCreateDto createDto = new ClientCreateDto("Jane", "Doe", "0987654321");
        Client createdClient = new Client(2L, "Jane", "Doe", "0987654321");
        ClientResponseDto responseDto = new ClientResponseDto(createdClient.getId(), "Client created successfully");

        Mockito.when(clientService.createClient(Mockito.any(Client.class))).thenReturn(createdClient);

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Client created successfully")));
    }



    @Test
    public void testUpdateClient() throws Exception {
        ClientUpdateDto updateDto = new ClientUpdateDto("Updated Name", "Updated LastName", "1234567890");
        Client updatedClient = new Client(1L, "Updated Name", "Updated LastName", "1234567890");

        Mockito.when(clientService.updateClient(Mockito.eq(1L), Mockito.any(ClientUpdateDto.class))).thenReturn(updatedClient);

        mockMvc.perform(MockMvcRequestBuilders.put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Client updated successfully")));
    }






}

