package com.batu.book_network.book;

import com.batu.book_network.request.BookRequest;
import com.batu.book_network.response.BookResponse;
import com.batu.book_network.response.BorrowedBookResponse;
import com.batu.book_network.response.UploadBookCoverPictureResponse;
import com.batu.book_network.common.PageResponse;
import com.batu.book_network.convert.BookMapper;
import com.batu.book_network.convert.Mapper;
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
    public ResponseEntity<Long> addBook(
        @org.springframework.web.bind.annotation.RequestBody @Valid BookRequest request,
        org.springframework.security.core.Authentication connectedUser
    ){
        return service.saveBook(request, connectedUser) == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(service.saveBook(request, connectedUser));
    }

    @GetMapping(value = "{book-id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable("book-id") Long bookId){
        return ResponseEntity.ok(service.findBookById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false)int size,
        Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
    }

    @GetMapping(value = "owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false)int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping(value = "borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping(value = "/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping(value = "shareable/{book-id}")
    public ResponseEntity<Long> updateShareableStatus(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updateShareableStatus(bookId, connectedUser));
    }

    @PatchMapping("archived/{book-id}")
    public ResponseEntity<Long> updateArchivedStatus(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.updatedArchivedStatus(bookId, connectedUser));
    }

    @PostMapping(value = "borrow/{book-id}")
    public ResponseEntity<Long> borrowBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    @PatchMapping(value = "borrow/return/{book-id}")
    public ResponseEntity<Long> returnBorrowedBook(
            @PathVariable("book-id") Long bookId,
            Authentication connectedUser
    ) {
        var request = bookMapper.toReturnBorrowedBook(bookId, connectedUser);
        return ResponseEntity.ok(service.returnBorrowedBook(request));
    }

    @PatchMapping(value = "borrow/return/approve/{book-id}")
    public ResponseEntity<Long> approveReturnBorrowedBook(
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
