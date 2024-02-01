package jasperwiese.dev.mybank.service;

import jasperwiese.dev.mybank.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
@Service
public class TransactionService {
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    private final String bankSlogan;

    private final JdbcTemplate jdbcTemplate;

    private final TransactionRepository transactionRepository;

    public TransactionService(@Value("${bank.slogan}")String bankSlogan, JdbcTemplate jdbcTemplate, TransactionRepository transactionRepository) {
        this.bankSlogan = bankSlogan;
        this.jdbcTemplate = jdbcTemplate;
        this.transactionRepository = transactionRepository;
    }
    @Transactional
    public Iterable<Transaction> findAll() {
        /*System.out.println("hit findall");
        return jdbcTemplate.query("SELECT * FROM TRANSACTIONS",
                TransactionService::mapRow);*/
        System.out.println("Executing findAll method");
        try {
            return transactionRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error in findAll method: " + e.getMessage());
            e.printStackTrace();
            throw e; // Rethrow the exception to see the full stack trace in logs
        }
    }
    @Transactional
    public Iterable<Transaction> findByIncomingId(String userId) {
        return transactionRepository.findbyUserId(userId);
    }
    @Transactional
    public Transaction create(BigDecimal amount, String reference){
        ZonedDateTime timestamp = LocalDateTime.now().atZone(DEFAULT_ZONE);
        Transaction transaction = new Transaction(amount,timestamp,reference, bankSlogan);
        return transactionRepository.save(transaction);
    }
    @Transactional
    public Transaction createFromInput(BigDecimal amount, String reference, String inputUser) {
        ZonedDateTime timestamp = LocalDateTime.now().atZone(DEFAULT_ZONE);
        Transaction transaction = new Transaction(amount, timestamp, reference, bankSlogan, inputUser);
        return transactionRepository.save(transaction);
    }

}
