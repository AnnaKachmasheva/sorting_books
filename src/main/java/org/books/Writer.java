package org.books;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.books.model.Book;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class Writer {

    private static final String FOLDER = "csv_book/";
    private static final String FILE_FORMAT = ".csv";
    private static final char DELIMITER = ';';

    private final String[] header = {"ISBN", "Nazev", "Autor", "Vydano"};


    void writeLineByLine(String fileName, HashMap<String, Book> books) {
        String path = getFilePath(fileName);
        try {
            File file = new File(path);

            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile, DELIMITER, CSVWriter.NO_QUOTE_CHARACTER,
                                                                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                                                    CSVWriter.DEFAULT_LINE_END);

            writer.writeNext(header);
            books.forEach((key, book) -> writer.writeNext(book.toArray()));

            writer.close();
        } catch (IOException e) {
            log.error("Error writing output file named {}", fileName);
        }
    }

    public String getFilePath(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(FOLDER)
                .append(fileName)
                .append(FILE_FORMAT)
                .toString();
    }

}
