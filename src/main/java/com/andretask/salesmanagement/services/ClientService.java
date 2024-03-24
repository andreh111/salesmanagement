package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.controllers.dto.ClientUpdateDto;
import com.andretask.salesmanagement.exceptions.ClientNotFoundException;
import com.andretask.salesmanagement.exceptions.DuplicateClientException;
import com.andretask.salesmanagement.models.Client;
import com.andretask.salesmanagement.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> fetchClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) {
        Optional<Client> existingClient = clientRepository.findByMobile(client.getMobile());
        if (existingClient.isPresent()) {
            throw new DuplicateClientException("Client with mobile number " + client.getMobile() + " already exists");
        }
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }


    public Client updateClient(Long id, ClientUpdateDto updatedClient) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setName(updatedClient.getName());
            client.setLastName(updatedClient.getLastName());
            client.setMobile(updatedClient.getMobile());
            return clientRepository.save(client);
        }
        throw new ClientNotFoundException("Client with id " + id + " not found");
    }
}
