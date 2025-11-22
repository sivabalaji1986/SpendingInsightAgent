------------------------------------------------------------
-- Sample Account
------------------------------------------------------------
INSERT INTO accounts(id, customer_name, currency, opened_on) VALUES
    ('A123', 'John Doe', 'SGD', '2022-03-01');


------------------------------------------------------------
-- Sample Transactions (November)
------------------------------------------------------------
INSERT INTO transactions(account_id, category, amount, date, merchant) VALUES
                                                                           ('A123', 'Food', 120.50, '2025-11-01', 'GrabFood'),
                                                                           ('A123', 'Travel', 580.00, '2025-11-03', 'Scoot Airlines'),
                                                                           ('A123', 'Shopping', 320.00, '2025-11-05', 'Uniqlo'),
                                                                           ('A123', 'Travel', 1400.00, '2025-11-18', 'Singapore Airlines'),
                                                                           ('A123', 'Bills', 210.00, '2025-11-20', 'SP Services');


------------------------------------------------------------
-- Sample Transactions (October for comparison)
------------------------------------------------------------
INSERT INTO transactions(account_id, category, amount, date, merchant) VALUES
                                                                           ('A123', 'Food', 110.00, '2025-10-03', 'GrabFood'),
                                                                           ('A123', 'Shopping', 200.00, '2025-10-09', 'Shopee'),
                                                                           ('A123', 'Bills', 180.00, '2025-10-19', 'SP Services'),
                                                                           ('A123', 'Travel', 200.00, '2025-10-21', 'Bus Ticket');
