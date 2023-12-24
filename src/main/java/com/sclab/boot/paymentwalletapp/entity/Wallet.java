package com.sclab.boot.paymentwalletapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import static com.sclab.boot.paymentwalletapp.entity.EntityConstant.preventSqlInjectionMsg;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @NotNull
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[^'\"\\\\]+$", message = preventSqlInjectionMsg)
    private String username;

    @Column(nullable = false)
    @NotNull

    @NotNull
    private BigDecimal balance;

    @Pattern(regexp = "^[A-Z]{3,4}$")
    @NotNull
    @NotBlank
    private String currencyCode;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Wallet setWalletOrDefault(Wallet newWallet) {
        if (newWallet == null) {
            return null;
        }
        this.id = Objects.requireNonNullElse(newWallet.getId(), this.id);
        this.username = Objects.requireNonNullElse(newWallet.getUsername(), this.username);
        this.balance = Objects.requireNonNullElse(newWallet.getBalance(), this.balance);
        this.currencyCode = Objects.requireNonNullElse(newWallet.getCurrencyCode(), this.currencyCode);
        return this;
    }

}