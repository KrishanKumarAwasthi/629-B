IF OBJECT_ID('FeePayments', 'U') IS NOT NULL
    DROP TABLE FeePayments;
GO

CREATE TABLE FeePayments (
    payment_id   INT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL CHECK (amount > 0),
    payment_date DATE NOT NULL
);
GO

PRINT 'Table FeePayments created successfully.';
PRINT '---------------------------------------------------------';
GO

PRINT 'PART A: Starting a successful transaction to insert 3 records.';

BEGIN TRANSACTION;

INSERT INTO FeePayments (payment_id, student_name, amount, payment_date) VALUES
(1, 'Ashish', 5000.00, '2024-06-01'),
(2, 'Smaran', 4500.00, '2024-06-02'),
(3, 'Vaibhav', 5500.00, '2024-06-03');

COMMIT TRANSACTION;

PRINT 'Transaction committed successfully.';
PRINT 'Current state of FeePayments table:';
SELECT * FROM FeePayments;
PRINT '---------------------------------------------------------';
GO

PRINT 'PART B: Starting a transaction that will fail (duplicate key).';

BEGIN TRANSACTION;

BEGIN TRY
    PRINT 'Attempting to insert a valid record for Kiran (ID=4)...';
    INSERT INTO FeePayments (payment_id, student_name, amount, payment_date)
    VALUES (4, 'Kiran', 6000.00, '2024-06-04');

    PRINT 'Attempting to insert a record with a duplicate ID=1...';
    INSERT INTO FeePayments (payment_id, student_name, amount, payment_date)
    VALUES (1, 'Ashish_New', 5100.00, '2024-06-05');
    
    COMMIT TRANSACTION;

END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT 'ERROR: Transaction failed and was completely rolled back.';
END CATCH;

PRINT 'Final state of FeePayments table after failed transaction:';
SELECT * FROM FeePayments;
PRINT '---------------------------------------------------------';
GO

PRINT 'PART C: Starting a transaction that will fail (NULL value).';

BEGIN TRANSACTION;

BEGIN TRY
    PRINT 'Attempting to insert a valid record for Riya (ID=5)...';
    INSERT INTO FeePayments (payment_id, student_name, amount, payment_date)
    VALUES (5, 'Riya', 7000.00, '2024-06-06');

    PRINT 'Attempting to insert a record with a NULL student_name...';
    INSERT INTO FeePayments (payment_id, student_name, amount, payment_date)
    VALUES (6, NULL, 4000.00, '2024-06-07');

    COMMIT TRANSACTION;
END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION;
    PRINT 'ERROR: Transaction failed due to constraint violation and was rolled back.';
END CATCH;

PRINT 'Final state of FeePayments table after another failed transaction:';
SELECT * FROM FeePayments;
PRINT '---------------------------------------------------------';
GO

PRINT 'PART D: ACID Properties Summary';
PRINT 'Atomicity:   Demonstrated by "all or nothing"COMMIT/ROLLBACK behavior.';
PRINT 'Consistency: The database always remains in a valid state, as defined by its constraints.';
PRINT 'Isolation:   (Conceptual) Uncommitted changes from failed transactions were not visible.';
PRINT 'Durability:  The 3 records inserted in Part A are now permanently stored.';
GO
