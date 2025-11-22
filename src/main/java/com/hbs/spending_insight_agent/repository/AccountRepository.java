package com.hbs.spending_insight_agent.repository;

import com.hbs.spending_insight_agent.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}

