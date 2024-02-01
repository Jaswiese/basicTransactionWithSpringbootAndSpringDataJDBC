package jasperwiese.dev.mybank.web;

import jakarta.validation.Valid;
import jasperwiese.dev.mybank.dto.TransactionDto;
import jasperwiese.dev.mybank.model.Transaction;
import jasperwiese.dev.mybank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public Iterable<Transaction> getTransactions(){
        return transactionService.findAll();
    }

    @PostMapping("/transaction")
    public Transaction createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.createFromInput(transactionDto.getAmount(), transactionDto.getReference(), transactionDto.getInputUser());
    }

    @GetMapping("/transaction/user/{userId}")
    public  Iterable<Transaction> getTransactionsByUserId(@PathVariable String userId){
        return transactionService.findByIncomingId(userId);
    }
}
