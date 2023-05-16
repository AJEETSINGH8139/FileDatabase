import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileDatabase {
    private static final String METADATA_FILE = "metadata.txt";
    private static final String TABLE_FILE = "table.txt";

    public static void main(String[] args) {
        createMetadataFile();

        // Get the SQL statement as input from the user
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the SQL statement:");
        String sqlStatement = input.nextLine();

        // Parse the SQL statement and execute the corresponding operation
        if (sqlStatement.startsWith("CREATE TABLE")) {
            createTable(sqlStatement);
        } else if (sqlStatement.startsWith("INSERT INTO")) {
            insertIntoTable(sqlStatement);
        }
    }

    private static void createMetadataFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(METADATA_FILE))) {
            writer.write("col1 INTEGER");
            writer.newLine();
            writer.write("col2 STRING");
            writer.newLine();
            // Add more column definitions as needed
        } catch (IOException e) {
            System.out.println("Error creating metadata file: " + e.getMessage());
        }
    }

    private static void createTable(String sqlStatement) {
        try {
            // Parse the column definitions from the SQL statement
            String columnList = sqlStatement.substring(sqlStatement.indexOf("(") + 1, sqlStatement.lastIndexOf(")"));
            String[] columns = columnList.split(",");
            List<String> columnNames = new ArrayList<>();
            List<String> columnTypes = new ArrayList<>();

            for (String column : columns) {
                String[] parts = column.trim().split("\\s+");
                columnNames.add(parts[0]);
                columnTypes.add(parts[1]);
            }

            // Write the column names and types to the metadata file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(METADATA_FILE))) {
                for (int i = 0; i < columnNames.size(); i++) {
                    writer.write(columnNames.get(i) + " " + columnTypes.get(i));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing metadata file: " + e.getMessage());
            }

            // Create an empty table file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(TABLE_FILE))) {
                // Do nothing, just create an empty table file
            } catch (IOException e) {
                System.out.println("Error creating table file: " + e.getMessage());
            }

            System.out.println("Table created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    private static void insertIntoTable(String sqlStatement) {
        try {
            // Parse the values from the SQL statement
            String valueList = sqlStatement.substring(sqlStatement.indexOf("(") + 1, sqlStatement.lastIndexOf(")"));
            String[] values = valueList.split(",");

            // Load the column names and types from the metadata file
            List<String> columnNames = new ArrayList<>();
            List<String> columnTypes = new ArrayList<>();

            try (Scanner scanner = new Scanner(METADATA_FILE)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split("\\s+");
                    columnNames.add(parts[0]);
                columnTypes.add(parts[1]);
                }
            }
                    // Validate the number of values provided matches the number of columns
        if (values.length != columnNames.size()) {
            throw new IllegalArgumentException("Invalid number of values provided.");
        }

        // Convert the values to the appropriate types
        List<Object> convertedValues = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            String value = values[i].trim();
            String type = columnTypes.get(i);

            if (type.equalsIgnoreCase("INTEGER")) {
                convertedValues.add(Integer.parseInt(value));
            } else if (type.equalsIgnoreCase("STRING")) {
                convertedValues.add(value);
            } else {
                throw new IllegalArgumentException("Unsupported data type: " + type);
            }
        }

        // Append the converted values to the table file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TABLE_FILE, true))) {
            for (Object convertedValue : convertedValues) {
                writer.write(convertedValue.toString());
                writer.write(",");
            }
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing table file: " + e.getMessage());
        }

        System.out.println("Row inserted successfully.");
    } catch (Exception e) {
        System.out.println("Error inserting row: " + e.getMessage());
    }
}
}
