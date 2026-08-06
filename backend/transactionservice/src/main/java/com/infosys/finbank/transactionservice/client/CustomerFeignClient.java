package com.infosys.finbank.transactionservice.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.finbank.transactionservice.client.dto.CustomerResponse;

@FeignClient(name = "customerservice")
public interface CustomerFeignClient {

    @GetMapping("/customer/{id}")
    CustomerResponse getCustomerById(@PathVariable UUID id);

}
