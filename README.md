﻿# FileDatabase

### Implementation in Java that demonstrates a simple file-based database supporting the CREATE TABLE and INSERT INTO operations as you described. It uses two separate text files: one for storing the metadata about the table (column names and types) and another for storing the table data.

### The program will prompt you to enter an SQL statement. You can enter either a CREATE TABLE statement or an INSERT INTO statement. The program will parse the statement, create the necessary files, and perform the corresponding operation (creating a table or inserting a row). Note that the program assumes the SQL statements are entered correctly according to the specified syntax.

### Please keep in mind that this implementation is a simple example for educational purposes and not meant for production use. It doesn't include error handling or support for more advanced SQL features.

To run this Java program, you need to have the JDK (Java Development Kit) installed on your system. Save the code to a file with a `.java` extension (e.g., `FileDatabase.java`). Then, open a terminal or command prompt, navigate to the directory where the file is located, and execute the following commands:

```shell
javac FileDatabase.java
java FileDatabase
