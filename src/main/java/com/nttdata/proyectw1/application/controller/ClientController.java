package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.Client;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RestController
@RequestMapping(name = "client")
public class ClientController {

    @PostMapping
    public Mono<String> createClient(@RequestBody Client client){
        return Mono.just("Successful Registration");
    }

    @PutMapping("/updateClient/{documentNumber}")
    public Mono<String> updateClient(@RequestBody Client client, @RequestAttribute String documentNumber){
        return Mono.just("Successful Update");
    }

    @GetMapping("/getClient/{documentNumber}")
    public Mono<Client> getClient(@RequestAttribute String documentNumber){
        return Mono.just(new Client());
    }

    @GetMapping("/getClients")
    public Flux<Client> getClients(){
        return Flux.just(new ArrayList<Client>().get(0));
    }

    @DeleteMapping("/deleteClient/{documentNumber}")
    public Mono<String> deleteClient(@RequestAttribute String documentNumber){
        return Mono.just("Successful Delete");
    }




}
