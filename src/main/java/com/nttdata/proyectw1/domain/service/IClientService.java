package com.nttdata.proyectw1.domain.service;

import ch.qos.logback.core.net.server.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {

    Mono<Void> createClient(Client client);

    Mono<Void> updateClient(Client client, String documentNumber);

    Mono<Client> getClient(String documentNumber);

    Flux<Client> getAllClients();

    Mono<Void> deleteClient(String documentNumber);

}
