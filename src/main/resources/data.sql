INSERT INTO CUSTOMER VALUES (1, 'hoje' ,'cassioate@gmail.com', '(83)9 9918-9488',  'Cassio', '$2a$12$29JgF23wW5O3ErM5yev2ieiGyXsOOGuS31eD75zXAUEa7FQ9BDJf.');
INSERT INTO CUSTOMER VALUES (2, 'hoje' ,'cassioate2@gmail.com', '(83)9 9918-9488',  'CassioTESTE', '$2a$12$29JgF23wW5O3ErM5yev2ieiGyXsOOGuS31eD75zXAUEa7FQ9BDJf.');
INSERT INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `create_dt`)
 VALUES (1, 186576453434, 'Savings', '123 Main Street, New York', CURDATE());

 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
 `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-7, 'Coffee Shop', 'Withdrawal', 30,34500,CURDATE()-7);
 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
 `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-6, 'Uber', 'Withdrawal', 100,34400,CURDATE()-6);
 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
 `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-5, 'Self Deposit', 'Deposit', 500,34900,CURDATE()-5);
 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
 `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-4, 'Ebay', 'Withdrawal', 600,34300,CURDATE()-4);
 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
 `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-2, 'OnlineTransfer', 'Deposit', 700,35000,CURDATE()-2);
 INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
 `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-1, 'Amazon.com', 'Withdrawal', 100,34900,CURDATE()-1);


INSERT INTO CARDS VALUES (1, 1, 20, NULL, NULL, NULL, 1, 25);
INSERT INTO CARDS VALUES (21, 1, 22, NULL, NULL, NULL, 1, 26);
INSERT INTO CARDS VALUES (31, 1, 223, NULL, NULL, NULL, 1, 27);

INSERT INTO AUTHORITIES (id, customer_id, name) VALUES (1, 1, 'ROLE_ADMIN');
INSERT INTO AUTHORITIES (id, customer_id, name) VALUES (2, 2, 'ROLE_USER');
