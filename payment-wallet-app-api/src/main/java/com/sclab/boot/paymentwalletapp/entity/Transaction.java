package com.sclab.boot.paymentwalletapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sclab.boot.paymentwalletapp.enumeration.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallet_transaction")
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
    @JoinColumn(name = "senderId", referencedColumnName = "id")
    private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "receiverId", referencedColumnName = "id")
    private Wallet receiver;

    @DecimalMin(value = "0.00001", inclusive = false)
    private BigDecimal amount;

    private String notes;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

}