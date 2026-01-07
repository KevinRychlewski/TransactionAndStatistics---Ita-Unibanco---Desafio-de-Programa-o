package com.rychlewski.transaction_statistics.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionRequestDTO {

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @NotNull
    private Instant timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timestamp = timeStamp;
    }
}
