package com.sclab.boot.paymentwalletapp.repository;

import com.sclab.boot.paymentwalletapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findTransactionBySenderId(UUID senderId);

    @Query(value = "SELECT SUM(t.amount) FROM wallet_transaction t " +
            "WHERE CAST(t.created_on AS DATE) = CAST(:createdOn AS DATE) " +
            "AND t.status <> 'FAILURE' AND t.sender_id = :senderId", nativeQuery = true)
    BigDecimal findSumOfAllTransactionInLast24Hours(@Param("senderId") String senderId,
                                                     @Param("createdOn") String createdOn);

}