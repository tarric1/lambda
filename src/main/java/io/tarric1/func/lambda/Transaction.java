package io.tarric1.func.lambda;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    public enum Type {
        WITHDRAWAL, DEPOSIT, TRANSFER, PAYMENT
    }
    
    public enum Status {
        TO_BE_PROCESSED, PROCESSED
    }

    private LocalDate executionDate;
    private BigDecimal amount;
    private Type type;
    private Status status;
}