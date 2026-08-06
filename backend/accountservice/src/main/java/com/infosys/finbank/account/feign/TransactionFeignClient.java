package com.infosys.finbank.account.feign;

import com.infosys.finbank.account.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "transactionservice",
        url = "http://localhost:8080"
)
public interface TransactionFeignClient {

    @GetMapping("/transactions/account/{accountId}")
    List<TransactionDto> getTransactions(@PathVariable("accountId") Long accountId);

}