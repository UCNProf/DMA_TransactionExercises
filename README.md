# Concurrency Control Exercises

The following exercises are based on a bank system with a single table holding information about accounts. Each account has an id, a name and an amount. The following script creates the table:

```
CREATE TABLE [Account](
	[Id] [int] NOT NULL,
	[Name] [nvarchar](10) NOT NULL,
	[Balance] [float] NOT NULL
);
```

For all the exercises you will need two or three accounts each with a balance of $200. You can use the script below to insert test data into the table.

```
TRUNCATE TABLE Account;

INSERT INTO Account (Id, Name, Balance) VALUES (1, 'a1', 200);

INSERT INTO Account (Id, Name, Balance) VALUES (2, 'a2', 200);

INSERT INTO Account (Id, Name, Balance) VALUES (3, 'a3', 200);
```

In these exercises you will work with transaction behavior with pessimistic or optimistic concurrency control. In addition, the last exercise is also about deadlocks. The overall goal of the exercises is for you to get an understanding of how transactions and concurrency control works together and how to choose the most appropriate method.   

When you work with the exercises you can implement them on any platform you want. It could be as stored procedures on a relational database system or in a programming language that supports transactions. It is possible though to solve the exsolve the exercises without doing this. You could also download a sample written in Java here.    

## Pessimistic Concurrency 

Consider the following transactions:

### Transaction 1

1.	Withdraw $100 from account 1
2.	Deposit the $100 in account 2
3.	Read the balance from both accounts

### Transaction 2

1.	Read the balance from account 1
2.	Add an interest amount of 10% to account 1

Examine the possible outcome of each transaction if they are issued concurrently with pessimistic concurrency control under each isolation level. Determine which locks are acquired on the data and when they are released. 

* READ_UNCOMMITTED
* READ_COMMITTED
* REPEATABLE_READ
* SERIALIZABLE

### Questions
1.	Which isolation level should the transactions use and why?
 
## Optimistic Concurrency 

Consider the following transactions:

### Transaction 1
1.	Read the balance from account 1
2.	Set account 1 balance to zero
3.	Deposit the amount read from account 1 in account 2

### Transaction 2
1.	Read the balance from account 1
2.	Add an interest amount of 10% to account 1

Examine the possible outcomes if these two transactions were to run concurrently with no concurrency control. 

### Questions
1.	What is the race condition?
2.	How should an optimistic concurrency check be implemented to avoid concurrency issues?

## Deadlock 

Examine the following transactions:

### Transaction 1
1.	Withdraw $100 from account 1
2.	Deposit the $100 in account 2
3.	Read the balance from both accounts

### Transaction 2
1.	Withdraw $50 from account 3
2.	Deposit the $50 in account 2
3.	Read the balance from both accounts

The transactions are started concurrently with the READ_COMMITTED isolation level and a deadlock occurs. 

### Questions
1.	What is the cause of the deadlock?
2.	How can it be solved if the isolation level must not be changed?
3.	Can it be solved simply by changing the isolation level? 
