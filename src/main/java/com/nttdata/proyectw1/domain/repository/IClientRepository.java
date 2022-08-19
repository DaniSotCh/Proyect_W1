package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.Client;
import reactor.core.publisher.Mono;

public interface IClientRepository  {
    Mono<Void> createClient(Client client);
}
