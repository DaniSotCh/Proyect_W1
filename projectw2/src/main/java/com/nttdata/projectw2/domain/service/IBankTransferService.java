package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.BankTransfer;
import reactor.core.publisher.Mono;

public interface IBankTransferService {

     Mono<BankTransfer> bankTransfer(BankTransfer bankTransfer);

}
