package com.sclab.boot.paymentwalletapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "id", unique = true)
    private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "id", unique = true)
    private Wallet receiver;

    @DecimalMin(value = "0.00001", inclusive = false)
    private BigDecimal amount;

    private String notes;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

}