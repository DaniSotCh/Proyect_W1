package com.nttdata.projectw2.application.controller;

import com.nttdata.projectw2.domain.entity.BankTransfer;
import com.nttdata.projectw2.domain.service.IBankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bankTransfer")
public class BankTransferController {

    @Autowired
    IBankTransferService bankTransferService;

    @PostMapping
    public Mono<BankTransfer> bankTransfer(@RequestBody BankTransfer bankTransfer){
        return bankTransferService.bankTransfer(bankTransfer);
    }

}
