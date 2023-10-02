package org.books;

import lombok.extern.slf4j.Slf4j;
import org.books.model.Book;

import java.util.HashMap;

@Slf4j
public class Main {

    private static final String OLD_BOOKS_FILE_NAME = "knihy_stare";
    private static final String NEW_BOOKS_FILE_NAME = "knihy_nove";

    /**
     * @param args args[0] - the name of file to the knihy.xml
     *             args[1] - "break year" number
     */
    public static void main(String[] args) {
        try {
            String path = args[0];
            int year = Integer.parseInt(args[1]);

            Parser parser = new Parser(path, year);
            parser.parse();
            HashMap<String, Book> oldBooks = parser.getOldBook();
            HashMap<String, Book> newBooks = parser.getNewBook();

            Writer writer = new Writer();
            writer.writeLineByLine(OLD_BOOKS_FILE_NAME, oldBooks);
            writer.writeLineByLine(NEW_BOOKS_FILE_NAME, newBooks);
        } catch (Exception e) {
            log.error("Wrong arguments {} and {}", args[0], args[1]);
        }
    }

}