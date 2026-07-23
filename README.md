# banking_app 🏦⌨️

This is a console banking app in Java, backed by PostgreSQL. It supports account registration, login, deposits/withdrawals, and bulk import from CSV

# Features
1. **Register 💻** a checking or savings account, with your own username/password or get one generated
2. **Login 🔐** with a session menu that lets you: view accounts, deposit, withdraw, logout
3. **CSV import 📂** for bulk account creation from an existing list
4. **Debit card and Pin Generation 💳**  for checking accounts
5. **All data stored in PostreSQL 🛢** so accounts and updates survive on restarts

# Tech used
1. Java 17
2. PostgreSQL
3. JDBC

# Setup
1. Install PostgreSQL
2. Create the database:
```sql
CREATE DATABASE bank;
```
3. create the tables by running `schema.sql` from inside psql:
   `\i schema.sql`
4. download the JDBC driver jar and add it to your project's classpath.
5. paste your password in the 'DB_PASSWORD' field or you can set up your password as an environment variable
6. Run Main.java
7. CSV import format is basic, no rows or headers just:
   `username,name,ssn,type,deposit`

