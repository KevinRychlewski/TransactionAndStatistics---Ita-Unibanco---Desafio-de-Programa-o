package com.rychlewski.transaction_statistics.service;

import com.rychlewski.transaction_statistics.dto.TransactionRequestDTO;
import com.rychlewski.transaction_statistics.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = new TransactionRepository();
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void shouldCreateTransactionWhenValid() {
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setAmount(BigDecimal.valueOf(100));
        dto.setTimeStamp(Instant.now());

        TransactionResult result = transactionService.save(dto);

        assertEquals(TransactionResult.CREATED, result);
    }

    @Test
    void shouldReturnInvalidWhenTimestampIsInFuture() {
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setAmount(BigDecimal.valueOf(50));
        dto.setTimeStamp(Instant.now().plusSeconds(10));

        TransactionResult result = transactionService.save(dto);

        assertEquals(TransactionResult.INVALID, result);
    }

    @Test
    void shouldReturnOutOfTimeWindowWhenTransactionIsOld() {
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setAmount(BigDecimal.valueOf(30));
        dto.setTimeStamp(Instant.now().minusSeconds(120));

        TransactionResult result = transactionService.save(dto);

        assertEquals(TransactionResult.OUT_OF_TIME_WINDOW, result);
    }
}
