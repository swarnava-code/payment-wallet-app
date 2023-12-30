package com.sclab.boot.paymentwalletapp.entity;

import com.sclab.boot.paymentwalletapp.advice.MinBalanceNotMetException;
import com.sclab.boot.paymentwalletapp.enumeration.WalletStatus;
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

@Entity
@Table
@Getter
@Setter
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

    @Column(nullable = false)
    @NotNull

    @NotNull
    private BigDecimal balance;

    @NotNull
    private WalletStatus status;

    @NotNull
    private int limitTransaction; // sender get warning if sender try to cross this value

    @Pattern(regexp = "^[A-Z]{3,4}$")
    @NotNull
    @NotBlank
    @Pattern(regexp = "INR")
    private String currencyCode;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", unique = true)
    private User user;

    public Wallet setWalletOrDefault(Wallet newWallet) {
        if (newWallet == null) {
            return null;
        }
        this.id = Objects.requireNonNullElse(newWallet.getId(), this.id);
        this.user = Objects.requireNonNullElse(newWallet.getUser(), this.user);
        this.balance = Objects.requireNonNullElse(newWallet.getBalance(), this.balance);
        this.currencyCode = Objects.requireNonNullElse(newWallet.getCurrencyCode(), this.currencyCode);
        return this;
    }

    /**
     * It should be >= 100
     */
    @PostPersist
    void checkMinimumBalanceInWallet() {
        System.out.println("balance.intValue(): "+balance.intValue());
        if (balance.intValue() < 100) {
            System.out.println("balance.intValue(): "+balance.intValue());
            throw new MinBalanceNotMetException("balance should be more than or equal to 100 in the time of wallet creation");
        }
    }

}