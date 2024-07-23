package com.batu.book_network.controllers;

import com.batu.book_network.config.request.*;
import com.batu.book_network.config.response.*;
import com.batu.book_network.config.mapper.BookMapper;
import com.batu.book_network.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/books/")
@Tag(name = "Book")
@RequiredArgsConstructor
public class BooksController {
    
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<SaveBookResponse> save(
            @RequestBody @Valid BookRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.save(request, connectedUser));
    }

    @GetMapping(value = "{book-id}")
    public ResponseEntity<FindBookByIdResponse> findBookById(@PathVariable("book-id") Long bookId){
        return ResponseEntity.ok(bookService.findBookById(bookId));
    }
    @PostMapping(value = "all-books")
    public ResponseEntity<FindAllBooksResponse> findAllBooks(@RequestBody FindAllBooksRequest request){
        return ResponseEntity.ok(bookService.findAllBooks(request));
    }

    @PostMapping (value = "displayable-books")
    public ResponseEntity<FindAllDisplayableBooksResponse> findAllDisplayableBooks(
        @RequestBody FindAllBooksRequest request,
        Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllDisplayableBooks(request, connectedUser));
    }

    @GetMapping(value = "owner")
    public ResponseEntity<FindAllBooksByOwnerResponse> findAllBooksByOwner(
            @RequestBody FindAllBooksByOwnerRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(request, connectedUser));
    }

    @GetMapping(value = "borrowed")
    public ResponseEntity<FindAllBorrowedBooksResponse> findAllBorrowedBooks(
            @RequestBody FindAllBorrowedBooksRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(request, connectedUser));
    }

    @GetMapping(value = "returned")
    public ResponseEntity<FindAllReturnedBooksResponse> findAllReturnedBooks(
            @RequestBody FindAllReturnedBooksRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllReturnedBooks(request, connectedUser));
    }

    @PatchMapping(value = "shareable/{book-id}")
    public ResponseEntity<UpdateShareableStatusResponse> updateShareableStatus(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toUpdateShareableStatusRequest(bookId, connectedUser);
        return ResponseEntity.ok(bookService.updateShareableStatus(request));
    }

    @PatchMapping("archived/{book-id}")
    public ResponseEntity<UpdateArchivedStatusResponse> updateArchivedStatus(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toUpdateArchivedStatusRequest(bookId, connectedUser);
        return ResponseEntity.ok(bookService.updateArchivedStatus(request));
    }

    @PostMapping(value = "borrow/{book-id}")
    public ResponseEntity<BorrowBookResponse> borrowBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toBorrowBookRequest(bookId, connectedUser);
        return ResponseEntity.ok(bookService.borrowBook(request));
    }

    @PatchMapping(value = "borrow/return/{book-id}")
    public ResponseEntity<ReturnBorrowedBookResponse> returnBorrowedBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toReturnBorrowedBook(bookId, connectedUser);
        return ResponseEntity.ok(bookService.returnBorrowedBook(request));
    }

    @PatchMapping(value = "borrow/return/approve/{book-id}")
    public ResponseEntity<ApproveReturnBorrowedBookResponse> approveReturnBorrowedBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toApproveReturnBorrowedBookRequest(bookId, connectedUser);
        return ResponseEntity.ok(bookService.approveReturnBorrowedBook(request));
    }

    @PostMapping(value = "cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<UploadBookCoverPictureResponse> uploadBookCoverPicture(
            @PathVariable("book-id") Long bookId,
            //@Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser) {
        var request = bookMapper.toUploadBookCoverPictureRequest(bookId, file);
        return ResponseEntity.ok(bookService.uploadBookCoverPicture(request, connectedUser));
    }

}
