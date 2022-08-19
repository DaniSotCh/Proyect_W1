package com.nttdata.proyectw1.domain.repository.impl;

import com.nttdata.proyectw1.domain.entity.Client;
import com.nttdata.proyectw1.domain.repository.IClientRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public class ClientRepositoryImpl implements IClientRepository {
    @Override
    public Mono<Void> createClient(Client client) {
        return null;
    }
}
