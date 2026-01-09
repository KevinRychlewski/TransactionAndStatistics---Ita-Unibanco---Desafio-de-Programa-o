package com.rychlewski.transaction_statistics.service;

import com.rychlewski.transaction_statistics.dto.StatisticsResponseDTO;
import com.rychlewski.transaction_statistics.dto.TransactionRequestDTO;
import com.rychlewski.transaction_statistics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
public class StatisticsService {

    private final TransactionRepository transactionRepository;

    public StatisticsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public StatisticsResponseDTO getStatistics() {
        List<TransactionRequestDTO> transactions = transactionRepository.findAll();
        Instant limit = Instant.now().minusSeconds(60);
        List<TransactionRequestDTO> recentTransactions = transactions.stream()
                .filter(t -> !t.getTimestamp().isBefore(limit))
                .toList();
        if (recentTransactions.isEmpty()) {
            return new StatisticsResponseDTO();
        }
        List<BigDecimal> values = recentTransactions.stream()
                .map(TransactionRequestDTO::getAmount)
                .toList();
        long count = values.size();
        BigDecimal sum = values.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avg = sum.divide(
                BigDecimal.valueOf(count),
                2,
                RoundingMode.HALF_UP
        );
        BigDecimal min = values.stream()
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal max = values.stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        StatisticsResponseDTO response = new StatisticsResponseDTO();
        response.setCount(count);
        response.setSum(sum);
        response.setAvg(avg);
        response.setMin(min);
        response.setMax(max);

        return response;
    }


}
