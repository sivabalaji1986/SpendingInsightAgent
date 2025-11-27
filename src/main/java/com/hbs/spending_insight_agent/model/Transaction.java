package com.hbs.spending_insight_agent.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "category")
    private String category;

    @Column(name = "merchant")
    private String merchant;

    @Column(name = "date")
    private LocalDate date;
}

