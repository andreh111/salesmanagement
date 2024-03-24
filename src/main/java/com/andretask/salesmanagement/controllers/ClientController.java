package com.andretask.salesmanagement.controllers;

import com.andretask.salesmanagement.controllers.dto.ClientCreateDto;
import com.andretask.salesmanagement.controllers.dto.ClientResponseDto;
import com.andretask.salesmanagement.controllers.dto.ClientUpdateDto;
import com.andretask.salesmanagement.exceptions.ClientNotFoundException;
import com.andretask.salesmanagement.models.Client;
import com.andretask.salesmanagement.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> fetchClients() {
        return clientService.fetchClients();
    }

    @PostMapping
    public ClientResponseDto createClient(@Validated @RequestBody ClientCreateDto clientCreateDto) {
        Client client = new Client();
        client.setName(clientCreateDto.getName());
        client.setLastName(clientCreateDto.getLastName());
        client.setMobile(clientCreateDto.getMobile());

        Client createdClient = clientService.createClient(client);
        return new ClientResponseDto(createdClient.getId(), "Client created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @Validated @RequestBody ClientUpdateDto clientDto) {
        try {
            Client updatedClient = clientService.updateClient(id, clientDto);
            return ResponseEntity.ok(new ClientResponseDto(updatedClient.getId(), "Client updated successfully"));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
