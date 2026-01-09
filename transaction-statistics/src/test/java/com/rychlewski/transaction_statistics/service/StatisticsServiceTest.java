package com.rychlewski.transaction_statistics.service;

import com.rychlewski.transaction_statistics.dto.StatisticsResponseDTO;
import com.rychlewski.transaction_statistics.dto.TransactionRequestDTO;
import com.rychlewski.transaction_statistics.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsServiceTest {

    private StatisticsService statisticsService;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = new TransactionRepository();
        statisticsService = new StatisticsService(transactionRepository);
    }

    @Test
    void shouldReturnEmptyStatisticsWhenNoTransactions() {
        StatisticsResponseDTO response = statisticsService.getStatistics();

        assertEquals(BigDecimal.ZERO, response.getSum());
        assertEquals(BigDecimal.ZERO, response.getAvg());
        assertEquals(BigDecimal.ZERO, response.getMin());
        assertEquals(BigDecimal.ZERO, response.getMax());
        assertEquals(0, response.getCount());
    }

    @Test
    void shouldCalculateStatisticsForRecentTransactions() {
        TransactionRequestDTO t1 = new TransactionRequestDTO();
        t1.setAmount(BigDecimal.valueOf(10));
        t1.setTimeStamp(Instant.now());

        TransactionRequestDTO t2 = new TransactionRequestDTO();
        t2.setAmount(BigDecimal.valueOf(20));
        t2.setTimeStamp(Instant.now());

        TransactionRequestDTO t3 = new TransactionRequestDTO();
        t3.setAmount(BigDecimal.valueOf(30));
        t3.setTimeStamp(Instant.now());

        transactionRepository.save(t1);
        transactionRepository.save(t2);
        transactionRepository.save(t3);

        StatisticsResponseDTO response = statisticsService.getStatistics();

        assertEquals(BigDecimal.valueOf(60), response.getSum());
        assertEquals(BigDecimal.valueOf(20).setScale(2), response.getAvg());
        assertEquals(BigDecimal.valueOf(10), response.getMin());
        assertEquals(BigDecimal.valueOf(30), response.getMax());
        assertEquals(3, response.getCount());
    }
}
