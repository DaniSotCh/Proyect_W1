package com.nttdata.proyectw1.domain.service;


import com.nttdata.proyectw1.domain.entity.Client;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements IClientService{

    @Override
    public Mono<Void> createClient(Client client) {
        return null;
    }

    @Override
    public Mono<Void> updateClient(Client client, String documentNumber) {
        return null;
    }

    @Override
    public Mono<Client> getClient(String documentNumber) {
        return null;
    }

    @Override
    public Flux<Client> getAllClients() {
        return null;
    }

    @Override
    public Mono<Void> deleteClient(String documentNumber) {
        return null;
    }
}
