package com.batu.book_network.book;

import com.batu.book_network.request.BookRequest;
import com.batu.book_network.request.ReturnBorrowedBookRequest;
import com.batu.book_network.request.UploadBookCoverPictureRequest;
import com.batu.book_network.request.ApproveReturnBorrowedBookRequest;
import com.batu.book_network.response.BookResponse;
import com.batu.book_network.response.BorrowedBookResponse;
import com.batu.book_network.response.UploadBookCoverPictureResponse;
import com.batu.book_network.common.PageResponse;
import com.batu.book_network.convert.BookMapper;
import com.batu.book_network.exception.OperationNotPermittedException;
import com.batu.book_network.file.FileStorageService;
import com.batu.book_network.history.BookTransactionHistory;
import com.batu.book_network.history.BookTransactionalHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.batu.book_network.user.User;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static com.batu.book_network.book.BookSpecification.*;
import static com.batu.book_network.history.BookTransactionalHistorySpecification.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionalHistoryRepository historyRepository;
    private final BookTransactionalHistoryRepository bookTransactionalHistoryRepository;
    private final FileStorageService fileStorageService;

    private static final String CREATED_DATE = "createdDate";
    private static final String BOOK_NOT_FOUND = "Book not found with the Id::";

    public Long saveBook(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        var book = bookMapper.requestToBook(request);
        book.setOwner(user);
        bookRepository.save(book);
        return book.getId();
    }

    public BookResponse findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::bookToResponse)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND + bookId));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATE).descending());
        Page<Book> books = bookRepository.findAll(withDisplayable(user.getId()), pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::bookToResponse)
                .toList();
       return PageResponse.from( new PageImpl<>(bookResponse, pageable, books.getTotalElements()));
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATE).descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::bookToResponse)
                .toList();
        return PageResponse.from( new PageImpl<>(booksResponse, pageable, books.getTotalElements()));
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATE));
        Page<BookTransactionHistory> borrowedBookHistories = historyRepository.findAll(withBorrowed(user.getId()), pageable);
        List<BorrowedBookResponse> borrowedBooksResponse = borrowedBookHistories.stream()
                .map(bookMapper::historyToBorrowedBookResponse)
                .toList();
        return PageResponse.from( new PageImpl<>(borrowedBooksResponse, pageable, borrowedBookHistories.getTotalElements()));
    }

    // findAllReturnedBooks:
    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_DATE).descending());
        Page<BookTransactionHistory> returnedBooksHistories = bookTransactionalHistoryRepository.findAll(isReturned(user.getId()), pageable);
        List<BorrowedBookResponse> borrowedBookResponses = returnedBooksHistories.stream()
                .map(bookMapper::historyToBorrowedBookResponse)
                .toList();
        return PageResponse.from( new PageImpl<>(borrowedBookResponses, pageable, returnedBooksHistories.getTotalElements()));
    }


    public Long updateShareableStatus(Long bookId, Authentication connectedUser) {
        var book = bookRepository.findById(bookId)
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + bookId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("This book is not owned bu this user!");
        }
        book.setSharable(!book.isSharable());
        bookRepository.save(book);
        return bookId;
    }

    public Long updatedArchivedStatus(Long bookId, Authentication connectedUser){
        var book = bookRepository.findById(bookId)
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + bookId));
        User user = (User) connectedUser.getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("This book is not owned by this user!");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Long borrowBook(Long bookId, Authentication connectedUser){
        var book = bookRepository.findById(bookId)
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + bookId));
        if(book.isArchived() || !book.isSharable()){
            throw new OperationNotPermittedException("Book is archived or not shareable!");
        }
        User user = (User) connectedUser.getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can't borrow your own book!");
        }
        final boolean isAlreadyBorrowedByUser = bookTransactionalHistoryRepository.exists(isAlreadyBorrowedByUser(bookId, user.getId()));
        if(isAlreadyBorrowedByUser){
            throw new OperationNotPermittedException("You already borrowed this book and it is still not returned or the return is not approved by the owner");
        }
        final boolean isAlreadyBorrowedByOtherUser = bookTransactionalHistoryRepository.exists(isAlreadyBorrowedByAnotherUser(bookId, user.getId()));
        if(isAlreadyBorrowedByOtherUser){
            throw new OperationNotPermittedException("This book is already borrowed by another user! Wait until returned!");
        }
        BookTransactionHistory history = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        // returns historyId
        return bookTransactionalHistoryRepository.save(history).getId();
    }

    // todo response classes will be generated soon.
    public Long returnBorrowedBook(ReturnBorrowedBookRequest request){
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        if (book.isArchived() || !book.isSharable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
        User user = (User) request.getConnectedUser().getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can't return or borrow your own book!");
        }
        BookTransactionHistory history = bookTransactionalHistoryRepository.findByBookIdAndUserId(request.getBookId(), user.getId())
                .orElseThrow( () -> new OperationNotPermittedException("You didn't borrowed this book!"));
        history.setReturned(true);
        return bookTransactionalHistoryRepository.save(history).getId();
    }

    public Long approveReturnBorrowedBook(ApproveReturnBorrowedBookRequest request){
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        if(book.isArchived() || !book.isSharable()){
            throw new OperationNotPermittedException("The requested book is archived or not shareable!");
        }
        User user = (User) request.getConnectedUser().getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can't borrow or return your own book!");
        }
        BookTransactionHistory history = bookTransactionalHistoryRepository.findByBookIdAndOwnerId(request.getBookId(), user.getId())
                .orElseThrow( () -> new OperationNotPermittedException("The book is not returned yet!"));
        history.setReturnApproved(true);
        return bookTransactionalHistoryRepository.save(history).getId();
    }

    public UploadBookCoverPictureResponse uploadBookCoverPicture(UploadBookCoverPictureRequest request) {
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        if(book.isArchived() || !book.isSharable()){
            throw new OperationNotPermittedException("The book is archived or not shareable at the moment!");
        }
        User user = (User) request.getConnectedUser().getPrincipal();
        var bookCoverPic = fileStorageService.saveFile(request.getFile(), user.getId());
        book.setBookCover(bookCoverPic);
        bookRepository.save(book);
        return bookMapper.bookCoverPictureResponse(book);
    }
}
