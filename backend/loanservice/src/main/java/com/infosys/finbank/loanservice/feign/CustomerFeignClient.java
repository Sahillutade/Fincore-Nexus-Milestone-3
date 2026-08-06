package com.infosys.finbank.loanservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.finbank.loanservice.feign.dto.CustomerResponse;

@FeignClient(name = "customerservice")
public interface CustomerFeignClient {

    @GetMapping("/customers/{customerId}")
    CustomerResponse getCustomerById(@PathVariable("customerId") String customerId);

    @GetMapping("/customers/{customerId}/kyc")
    Boolean isKycVerified(@PathVariable("customerId") String customerId);

}
