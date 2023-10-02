package org.books.helper;

import org.books.model.Author;
import org.books.model.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVReader {

    private static final String DELIMITER = ";";

    public HashMap<String, Book> read(String fileName) {
        HashMap<String, Book> bookHashMap = new HashMap<>();

        boolean isFirstRow = true;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!isFirstRow) {
                    String[] values = line.split(DELIMITER);
                    String ISBN = values[0];
                    String name = values[1];
                    int year = Integer.parseInt(values[3]);
                    String authorFullName = values[2];
                    String[] authorNames = authorFullName.split(" ");
                    String authorFirstName = authorNames[0];
                    String authorLastName = authorNames[1];

                    Author author = Author.builder()
                            .firstName(authorFirstName)
                            .lastName(authorLastName)
                            .build();

                    Book book = Book.builder()
                            .name(name)
                            .year(year)
                            .ISBN(ISBN)
                            .author(author)
                            .build();

                    bookHashMap.put(ISBN, book);
                }

                if (isFirstRow)
                    isFirstRow = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bookHashMap;
    }
}
