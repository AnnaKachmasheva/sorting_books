package org.books;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @ParameterizedTest
    @CsvSource({
            "1000, 0, 15",
            "3000, 15, 0",
            "2000, 1, 14",
            "2016, 6, 9"
    })
    void parse(int year, int expectedOldBookCount, int expectedNewBookCount) {
        String path = "src/main/resources/knihy.xml";

        Parser parser = new Parser();
        parser.parse(path, year);

        int countOldBook = parser.getOldBook().size();
        int countNewBook = parser.getNewBook().size();

        assertEquals(expectedOldBookCount, countOldBook);
        assertEquals(expectedNewBookCount, countNewBook);
    }

}