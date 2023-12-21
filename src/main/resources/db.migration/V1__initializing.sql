-- Create Transaction table
CREATE TABLE Transaction
(
    transactionReference     UUID PRIMARY KEY,
    amount                   DECIMAL,
    transactionFee           DECIMAL,
    billedAmount             DECIMAL,
    description              VARCHAR(255),
    dateCreated              TIMESTAMP,
    status                   VARCHAR(255),
    commissionWorthy         BOOLEAN,
    commission               DECIMAL,
    sourceAccountNumber      VARCHAR(255),
    destinationAccountNumber VARCHAR(255),
    account_number           BIGINT,
    FOREIGN KEY (account_number) REFERENCES Account (id)
);

-- Create Account table
CREATE TABLE Account
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    accountNumber VARCHAR(255),
    accountName   VARCHAR(255),
    balance       DECIMAL,
    user_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES User (id)
);

CREATE TABLE Transaction_Account
(
    transactionReference UUID,
    account_number       BIGINT,
    FOREIGN KEY (transactionReference) REFERENCES Transaction (transactionReference),
    FOREIGN KEY (account_number) REFERENCES Account (id)
);
