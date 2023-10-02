package org.books.model;

import lombok.Builder;

@Builder
public record Author(
        String firstName,
        String lastName) {

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}