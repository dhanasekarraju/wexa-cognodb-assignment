package com.wexa.talentgraph;

import com.wexa.talentgraph.service.SeedDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TalentGraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalentGraphApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(SeedDataService seedDataService) {
        return args -> {
            try {
                System.out.println("Seeding initial data...");
                seedDataService.seedData();
                System.out.println("Data seeding completed.");
            } catch (Exception e) {
                System.err.println("Error during data seeding: " + e.getMessage());
                e.printStackTrace();
                // Continue application startup even if seed fails
            }
        };
    }
}