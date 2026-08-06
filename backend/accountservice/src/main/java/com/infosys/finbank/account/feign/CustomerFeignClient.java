package com.infosys.finbank.account.feign;

import java.util.UUID;

import com.infosys.finbank.account.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customerservice",
        url = "http://localhost:8081"
)
public interface CustomerFeignClient {

    @GetMapping("/customer/{id}")
    CustomerDto getCustomer(@PathVariable("id") UUID id);

}