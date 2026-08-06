package com.infosys.finbank.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.finbank.transactionservice.client.dto.AccountResponse;

@FeignClient(name = "accountservice")
public interface AccountFeignClient {

    @GetMapping("/account/{id}")
    AccountResponse getAccountById(@PathVariable Long id);

}
