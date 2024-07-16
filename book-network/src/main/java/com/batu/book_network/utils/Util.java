package com.batu.book_network.utils;

public class Util {

    public static <T> T nvl(T param1, T param2) {
        return param1 != null ? param1 : param2;
    }

    private Util() {
        throw new IllegalStateException("Utility class");
    }
}
