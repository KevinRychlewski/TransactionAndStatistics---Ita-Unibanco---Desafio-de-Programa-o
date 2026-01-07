package com.rychlewski.transaction_statistics.service;

import com.rychlewski.transaction_statistics.dto.TransactionRequestDTO;
import com.rychlewski.transaction_statistics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResult save(TransactionRequestDTO dto) {
        if (dto.getAmount().compareTo(BigDecimal.ZERO) < 0 || dto.getTimestamp().isAfter(Instant.now())) {
            return TransactionResult.INVALID;
        }
        if (dto.getTimestamp().isBefore(Instant.now().minusSeconds(60))) {
            return TransactionResult.OUT_OF_TIME_WINDOW;
        }
        transactionRepository.save(dto);
        return TransactionResult.CREATED;
    }

    public void clearTransactions() {
        transactionRepository.clear();
    }
}
