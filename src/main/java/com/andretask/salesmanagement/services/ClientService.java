package com.andretask.salesmanagement.services;

import com.andretask.salesmanagement.controllers.dto.ClientUpdateDto;
import com.andretask.salesmanagement.exceptions.ClientNotFoundException;
import com.andretask.salesmanagement.exceptions.DuplicateClientException;
import com.andretask.salesmanagement.models.Client;
import com.andretask.salesmanagement.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing clients.
 */
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    /**
     * Constructs a new ClientService with the given ClientRepository.
     *
     * @param clientRepository the repository used for client operations
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Fetches all clients from the repository.
     *
     * @return a list of all clients
     */
    public List<Client> fetchClients() {
        return clientRepository.findAll();
    }

    /**
     * Creates a new client and saves it to the repository.
     *
     * @param client the client to be created
     * @return the created client
     * @throws DuplicateClientException if a client with the same mobile number already exists
     */
    public Client createClient(Client client) {
        Optional<Client> existingClient = clientRepository.findByMobile(client.getMobile());
        if (existingClient.isPresent()) {
            throw new DuplicateClientException("Client with mobile number " + client.getMobile() + " already exists");
        }
        return clientRepository.save(client);
    }

    /**
     * Retrieves a client by its ID.
     *
     * @param id the ID of the client to be retrieved
     * @return the client with the specified ID, or null if not found
     */
    public Client getClientById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    /**
     * Updates a client with the given ID using the provided updated client information.
     *
     * @param id the ID of the client to be updated
     * @param updatedClient the updated client information
     * @return the updated client
     * @throws ClientNotFoundException if a client with the specified ID is not found
     */
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
