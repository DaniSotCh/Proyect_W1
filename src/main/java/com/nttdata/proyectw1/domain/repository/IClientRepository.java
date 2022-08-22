package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IClientRepository extends MongoRepository<Client, String> { }
