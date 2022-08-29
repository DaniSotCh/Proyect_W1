package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.BankTransfer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BankTransferServiceImpl implements IBankTransferService{
    @Override
    public Mono<BankTransfer> bankTransfer(BankTransfer bankTransfer) {
        return null;
    }
}
