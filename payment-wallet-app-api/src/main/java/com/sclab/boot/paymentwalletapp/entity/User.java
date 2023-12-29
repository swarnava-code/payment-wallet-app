package com.sclab.boot.paymentwalletapp.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sclab.boot.paymentwalletapp.enumeration.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import static com.sclab.boot.paymentwalletapp.util.EntityConstant.preventSqlInjectionMsg;

@Entity
@Table(name = "wallet_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2)
    @Pattern(regexp = "^[a-zA-Z '.]+$")
    private String name;

    @Size(min = 4)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[^'\"\\\\]+$", message = preventSqlInjectionMsg)
    @Column(unique = true)
    private String username;

    @Pattern(regexp = ".*[0-9].*", message = "Must contain at least one number")
    @Pattern(regexp = ".*[a-z].*", message = "Must contain at least one small letter")
    @Pattern(regexp = ".*[A-Z].*", message = "Must contain at least one capital letter")
    @Pattern(regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", message = "Must contain at least one special character")
    @Pattern(regexp = "^[^'\"\\\\]+$", message = preventSqlInjectionMsg)
    @Size(min = 8, max = 20)
    @NotBlank
    @NotNull
    @Getter(AccessLevel.NONE)
    private String password;

    private Authority authority;

    @NotNull
    @NotBlank
    @Size(min = 9)
    private String address;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Pattern(regexp = "^[0-9 +-]+$")
    private String phone;

    @NotNull
    @Min(18)
    @Max(value = 123, message = "122 years and 164 days is the longest documented and verified human lifespan")
    private byte age;

    private boolean verified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Wallet wallet;

    @PostPersist
    private void setValuesCreationTime() {
        authority = authority == null ? Authority.MEMBER : authority;
    }

}