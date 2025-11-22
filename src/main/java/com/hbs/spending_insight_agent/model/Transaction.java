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
    private String id;

    private String accountId;
    private BigDecimal amount;
    private String category;
    private String merchant;
    private LocalDate date;
}

