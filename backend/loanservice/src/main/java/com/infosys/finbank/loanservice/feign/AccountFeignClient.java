package com.infosys.finbank.loanservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.finbank.loanservice.feign.dto.AccountResponse;

@FeignClient(name = "accountservice")
public interface AccountFeignClient {

    @GetMapping("/accounts/{accountId}")
    AccountResponse getAccount(@PathVariable("accountId") String accountId);

    @GetMapping("/accounts/{accountId}/exists")
    Boolean accountExists(@PathVariable("accountId") String accountId);

}
