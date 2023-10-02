package org.books.model;

import lombok.Builder;

@Builder
public record Author(
        String firstName,
        String lastName) {

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(firstName)
                            .append(" ")
                            .append(lastName)
                            .toString();
    }

}