package io.github.athirson010.financialPlanning;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class FinancialPlanningApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancialPlanningApplication.class, args);
    }
}
