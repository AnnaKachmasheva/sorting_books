package org.books;

import org.books.helper.CSVReader;
import org.books.helper.Generator;
import org.books.model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WriterTest {

    private static Generator generator;

    @BeforeAll
    static void init() {
        generator = new Generator();
    }

    @Test
    void writeLineByLine() {
        Writer writer = new Writer();
        HashMap<String, Book> expectedBooks = generator.getHashMapBook();
        String fileName = "old_books_test";

        writer.writeLineByLine(fileName, expectedBooks);

        CSVReader csvReader = new CSVReader();
        String file = writer.getFilePath(fileName);
        HashMap<String, Book> resultBooks = csvReader.read(file);

        assertEquals(expectedBooks.size(), resultBooks.size());
        for (Map.Entry<String, Book> entryExpected : expectedBooks.entrySet()) {
            String key = entryExpected.getKey();
            Book expectedBook = entryExpected.getValue();
            Book resultBook = resultBooks.get(key);

            assertEquals(expectedBook.ISBN(), resultBook.ISBN());
            assertEquals(expectedBook.name(), resultBook.name());
            assertEquals(expectedBook.year(), resultBook.year());
            assertEquals(expectedBook.author().lastName(), resultBook.author().lastName());
            assertEquals(expectedBook.author().firstName(), resultBook.author().firstName());
        }
    }

}