package com.hbs.spending_insight_agent.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "id")
    private String accountId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "currency")
    private String currency;

    @Column(name = "opened_on")
    private LocalDate openedOn;
}

