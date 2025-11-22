------------------------------------------------------------
-- ACCOUNTS TABLE
------------------------------------------------------------
CREATE TABLE accounts (
                          id VARCHAR(50) PRIMARY KEY,
                          customer_name VARCHAR(100),
                          currency VARCHAR(10),
                          opened_on DATE
);

------------------------------------------------------------
-- TRANSACTIONS TABLE
------------------------------------------------------------
CREATE TABLE transactions (
                              id IDENTITY PRIMARY KEY,
                              account_id VARCHAR(50),
                              category VARCHAR(50),
                              amount DECIMAL(10,2),
                              date DATE,
                              merchant VARCHAR(100),
                              FOREIGN KEY (account_id) REFERENCES accounts(id)
);
