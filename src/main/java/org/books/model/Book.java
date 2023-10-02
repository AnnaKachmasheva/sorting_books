package org.books.model;

import lombok.Builder;

@Builder
public record Book(
        String ISBN,
        Author author,
        String name,
        int year) {

    public String[] toArray() {
        return new String[]{ISBN, name, author.toString(), Integer.toString(year)};
    }

}