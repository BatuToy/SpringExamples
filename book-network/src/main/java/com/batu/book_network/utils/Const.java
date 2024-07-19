package com.batu.book_network.utils;

import lombok.Data;

@Data
public class Const {
    public static final String CREATED_DATE = "createdDate";
    public static final String BOOK_NOT_FOUND = "Book not found with the Id::";

    private Const(){
        throw new IllegalStateException("const cant be declared!");
    }
}
