package com.rychlewski.transaction_statistics.controller;

import com.rychlewski.transaction_statistics.dto.TransactionRequestDTO;
import com.rychlewski.transaction_statistics.service.TransactionResult;
import com.rychlewski.transaction_statistics.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> saveTransaction(@RequestBody @Valid TransactionRequestDTO dto) {
        TransactionResult result = transactionService.save(dto);
        return switch (result) {
            case CREATED ->  ResponseEntity.status(201).build();
            case OUT_OF_TIME_WINDOW -> ResponseEntity.noContent().build();
            case INVALID -> ResponseEntity.unprocessableEntity().build();
        };
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTransactions() {
        transactionService.clearTransactions();
        return ResponseEntity.noContent().build();
    }

}
