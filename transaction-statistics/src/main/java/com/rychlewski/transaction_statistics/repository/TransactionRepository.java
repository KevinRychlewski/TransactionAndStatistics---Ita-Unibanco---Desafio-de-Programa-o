package com.rychlewski.transaction_statistics.repository;

import com.rychlewski.transaction_statistics.dto.TransactionRequestDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class TransactionRepository {

    private final Queue<TransactionRequestDTO> transactions = new ConcurrentLinkedQueue<>();

    public void save(TransactionRequestDTO transactionRequestDTO) {
        transactions.add(transactionRequestDTO);
    }

    public List<TransactionRequestDTO> findAll() {
        return new ArrayList<>(transactions);
    }

    public void clear() {
        transactions.clear();
    }

}
