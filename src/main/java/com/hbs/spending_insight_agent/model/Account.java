package com.hbs.spending_insight_agent.model;

import jakarta.persistence.*;
import lombok.*;

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
}

