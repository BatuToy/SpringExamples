package com.batu.book_network.controllers;

import com.batu.book_network.request.BookRequest;
import com.batu.book_network.request.FindAllBooksByOwnerRequest;
import com.batu.book_network.request.FindAllBooksRequest;
import com.batu.book_network.request.FindAllBorrowedBooksRequest;
import com.batu.book_network.request.FindAllReturnedBooksRequest;
import com.batu.book_network.response.*;
import com.batu.book_network.convert.BookMapper;
import com.batu.book_network.services.BookService;
import io.swagger.v3.oas.annotations.Parameter;
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
    
    private final BookService service;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<SaveBookResponse> save(
        @RequestBody @Valid BookRequest request,
        Authentication connectedUser
    ){

        return service.save(bookMapper.toSaveBookRequest(request, connectedUser)) == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(service.save(  bookMapper.toSaveBookRequest(request, connectedUser)));
    }

    @GetMapping(value = "{book-id}")
    public ResponseEntity<FindBookByIdResponse> findBookById(@PathVariable("book-id") Long bookId){
        var request = bookMapper.toFindBookByIdRequest(bookId);
        return ResponseEntity.ok(service.findBookById(request));
    }

    @GetMapping
    public ResponseEntity<FindAllBooksResponse> findAllBooks(
        @RequestBody FindAllBooksRequest request,
        Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooks(request, connectedUser));
    }

    @GetMapping(value = "owner")
    public ResponseEntity<FindAllBooksByOwnerResponse> findAllBooksByOwner(
            @RequestBody FindAllBooksByOwnerRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooksByOwner(request, connectedUser));
    }

    @GetMapping(value = "borrowed")
    public ResponseEntity<FindAllBorrowedBooksResponse> findAllBorrowedBooks(
            @RequestBody FindAllBorrowedBooksRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBorrowedBooks(request, connectedUser));
    }

    @GetMapping(value = "/returned")
    public ResponseEntity<FindAllReturnedBooksResponse> findAllReturnedBooks(
            @RequestBody FindAllReturnedBooksRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllReturnedBooks(request, connectedUser));
    }

    @PatchMapping(value = "shareable/{book-id}")
    public ResponseEntity<UpdateShareableStatusResponse> updateShareableStatus(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toUpdateShareableStatusRequest(bookId, connectedUser);
        return ResponseEntity.ok(service.updateShareableStatus(request));
    }

    @PatchMapping("archived/{book-id}")
    public ResponseEntity<UpdateArchivedStatusResponse> updateArchivedStatus(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toUpdateArchivedStatusRequest(bookId, connectedUser);
        return ResponseEntity.ok(service.updateArchivedStatus(request));
    }

    @PostMapping(value = "borrow/{book-id}")
    public ResponseEntity<BorrowBookResponse> borrowBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toBorrowBookRequest(bookId, connectedUser);
        return ResponseEntity.ok(service.borrowBook(request));
    }

    @PatchMapping(value = "borrow/return/{book-id}")
    public ResponseEntity<ReturnBorrowedBookResponse> returnBorrowedBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toReturnBorrowedBook(bookId, connectedUser);
        return ResponseEntity.ok(service.returnBorrowedBook(request));
    }

    @PatchMapping(value = "borrow/return/approve/{book-id}")
    public ResponseEntity<ApproveReturnBorrowedBookResponse> approveReturnBorrowedBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toApproveReturnBorrowedBookRequest(bookId, connectedUser);
        return ResponseEntity.ok(service.approveReturnBorrowedBook(request));
    }

    @PostMapping(value = "cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<UploadBookCoverPictureResponse> uploadBookCoverPicture(
            @PathVariable("book-id") Long bookId,
            @Parameter()
            @RequestPart MultipartFile file,
            Authentication connectedUser) {
        var request = bookMapper.toUploadBookCoverPictureRequest(bookId, connectedUser,file);
        var  response = service.uploadBookCoverPicture(request);
        return ResponseEntity.ok(response);
    }
}