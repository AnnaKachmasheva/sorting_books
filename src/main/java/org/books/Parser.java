package org.books;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.books.model.Author;
import org.books.model.Book;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;

@Slf4j
@Getter
public class Parser {

    private static final String LIST = "Seznam";
    private static final String BOOK = "Kniha";
    private static final String BOOK_ISBN = "ISBN";
    private static final String BOOK_YEAR = "Vydano";
    private static final String NAME = "Nazev";
    private static final String AUTHOR = "Autor";
    private static final String AUTHOR_FIRST_NAME = "Jmeno";
    private static final String AUTHOR_LAST_NAME = "Prijmeni";

    private HashMap<String, Book> oldBook = new HashMap<>(); // before year
    private HashMap<String, Book> newBook = new HashMap<>(); // in and after year


    void parse(String path, int year) {
        try {
            File file = Paths.get(path).normalize().toFile();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));

            String firstName = null;
            String lastName = null;
            String ISBN = null;
            String name = null;
            Author author = null;
            int releaseYear = 0;
            boolean isTagName = false;
            boolean isTagList = true;

            while (eventReader.hasNext() && isTagList) {

                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {

                    StartElement startElement = event.asStartElement();

                    switch (startElement.getName().getLocalPart()) {
                        case BOOK -> {
                            Attribute ISBNAttr = startElement.getAttributeByName(new QName(BOOK_ISBN));
                            Attribute releaseYearAttr = startElement.getAttributeByName(new QName(BOOK_YEAR));

                            ISBN = ISBNAttr.getValue();
                            releaseYear = Integer.parseInt(releaseYearAttr.getValue());
                        }
                        case AUTHOR -> {
                            Attribute firstNameAttr = startElement.getAttributeByName(new QName(AUTHOR_FIRST_NAME));
                            Attribute lastNameAttr = startElement.getAttributeByName(new QName(AUTHOR_LAST_NAME));

                            firstName = firstNameAttr.getValue();
                            lastName = lastNameAttr.getValue();
                        }
                        case NAME -> isTagName = true;
                    }

                }

                if (event.isCharacters() && isTagName) {
                    Characters characters = event.asCharacters();
                    name = characters.getData();
                }

                if (event.isEndElement()) {

                    EndElement endElement = event.asEndElement();

                    switch (endElement.getName().getLocalPart()) {
                        case LIST -> isTagList = false;
                        case BOOK -> {
                            Book book = Book.builder()
                                            .ISBN(ISBN)
                                            .name(name)
                                            .year(releaseYear)
                                            .author(author)
                                            .build();
                            addBook(book, year);
                        }
                        case AUTHOR -> author = Author.builder()
                                                    .firstName(firstName)
                                                    .lastName(lastName)
                                                    .build();
                        case NAME -> isTagName = false;
                    }

                }
            }
        } catch (FileNotFoundException e) {
            log.error("File with path:{} not found", path);
        } catch (XMLStreamException e) {
            log.error("XMLStreamException: ", e);
        }

    }

    private void addBook(Book book, int year) {
        String key = book.ISBN();
        int releaseYear = book.year();

        if (releaseYear < year) {
            oldBook.put(key, book);
            return;
        }
        newBook.put(key, book);
    }
}