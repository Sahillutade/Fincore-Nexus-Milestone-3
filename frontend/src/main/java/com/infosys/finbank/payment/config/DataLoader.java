package com.infosys.finbank.payment.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.infosys.finbank.payment.model.Beneficiary;
import com.infosys.finbank.payment.repository.BeneficiaryRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final BeneficiaryRepository beneficiaryRepository;

    public DataLoader(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (beneficiaryRepository.count() == 0) {
            beneficiaryRepository.save(new Beneficiary("Jane Doe", "ACC1002", "FINB0001001", true));
            beneficiaryRepository.save(new Beneficiary("Rahul Verma", "ACC1003", "FINB0001001", true));
            System.out.println("--> Seeded Beneficiary Records Successfully!");
        }
    }
}