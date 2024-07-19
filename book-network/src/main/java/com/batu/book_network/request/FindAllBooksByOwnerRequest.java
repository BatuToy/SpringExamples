package com.batu.book_network.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FindAllBooksByOwnerRequest {
    private int page;
    private int size;
}   
