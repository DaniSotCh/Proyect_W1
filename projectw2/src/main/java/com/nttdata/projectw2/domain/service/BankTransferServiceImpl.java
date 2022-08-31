package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.BankAccount;
import com.nttdata.projectw2.domain.entity.BankTransfer;
import com.nttdata.projectw2.domain.entity.Passive;
import com.nttdata.projectw2.domain.util.constant.MovementTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

import static com.nttdata.projectw2.domain.util.constant.MovementTypeEnum.DEPOSIT;
import static com.nttdata.projectw2.domain.util.constant.MovementTypeEnum.WITHDRAWAL;

@Service
public class BankTransferServiceImpl implements IBankTransferService{

    private final WebClient webClient;

    public BankTransferServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Mono<BankTransfer> bankTransfer(BankTransfer bankTransfer) {

        Passive passiveByAccountOrigin = null;
        Passive passiveByAccountTarget = null;

        String ac1=bankTransfer.getOriginAccountNumber();
        String ac2=bankTransfer.getTargetAccountNumber();

        try {
            Mono<Passive> passiveMono = this.webClient.get().uri("/bankAccount/getProduct/{ac1}", ac1).retrieve().bodyToMono(Passive.class);
            Mono<Passive> passiveMono2 = this.webClient.get().uri("/bankAccount/getProduct/{ac2}", ac2).retrieve().bodyToMono(Passive.class);

        passiveMono.flatMap(r -> {
            BankAccount b1 = new BankAccount();
            b1.setAmount(bankTransfer.getAmount());
            b1.setAccountNumber(r.getAccountNumber());
            b1.setProductType(r.getProductType());
            b1.setMovementType(WITHDRAWAL);
            return this.webClient.post().uri("/bankAccount").body(b1, BankAccount.class).retrieve().bodyToMono(BankAccount.class);
        }).subscribe();
        passiveMono2.flatMap(r->{
            BankAccount b2 = new BankAccount();
            b2.setAmount(bankTransfer.getAmount());
            b2.setAccountNumber(r.getAccountNumber());
            b2.setProductType(r.getProductType());
            b2.setMovementType(DEPOSIT);
            return this.webClient.post().uri("/bankAccount").body(b2, BankAccount.class).retrieve().bodyToMono(BankAccount.class);
        }).subscribe();

        }catch (Exception e){
            e.printStackTrace();
        }


        return Mono.just(bankTransfer);
    }
}
