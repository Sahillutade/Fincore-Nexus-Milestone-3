package com.infosys.finbank.customerservice.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accountservice", url = "http://localhost:8082")
public interface AccountFeignClient {

    @GetMapping("/accounts/customer/{custId}")
    List<Account> getAccountsByCustomerId(@PathVariable("custId") UUID custId);

}
