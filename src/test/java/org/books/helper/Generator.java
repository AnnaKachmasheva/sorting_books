package org.books.helper;

import org.books.model.Author;
import org.books.model.Book;

import java.util.HashMap;
import java.util.Random;

public class Generator {

    private final Random random = new Random();

    public HashMap<String, Book> getHashMapBook() {
        HashMap<String, Book> bookHashMap = new HashMap<>();
        int countBooks = random.nextInt(1, 10);
        while (countBooks > 0) {
            Book book = getBook();
            bookHashMap.put(book.ISBN(), book);
            countBooks--;
        }
        return bookHashMap;
    }

    private Book getBook() {
        Author author = Author.builder()
                .firstName(randomStringWithParameter("firstName"))
                .lastName(randomStringWithParameter("lastName"))
                .build();

        return Book.builder()
                .author(author)
                .ISBN(randomStringWithParameter("ISBN"))
                .name(randomStringWithParameter("name"))
                .year(randomInt())
                .build();
    }

    private String randomStringWithParameter(String parameter) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(parameter)
                .append('_')
                .append(random.nextInt())
                .toString();
    }

    private int randomInt() {
        return random.nextInt();
    }

}
