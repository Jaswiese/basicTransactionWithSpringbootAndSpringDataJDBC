package jasperwiese.dev.mybank.service;

import jasperwiese.dev.mybank.model.Transaction;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
    @Query("SELECT id, amount, timestamp, reference, bank_slogan, input_user FROM \"transactions\" WHERE input_user = :userId")
    Iterable<Transaction> findbyUserId(@Param("userId") String userId);
}
