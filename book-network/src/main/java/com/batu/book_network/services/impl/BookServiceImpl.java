package com.batu.book_network.services.impl;

import com.batu.book_network.config.request.*;
import com.batu.book_network.config.response.*;
import com.batu.book_network.entites.Book;
import com.batu.book_network.repositories.BookRepository;
import com.batu.book_network.config.mapper.BookMapper;
import com.batu.book_network.config.exception.OperationNotPermittedException;
import com.batu.book_network.entites.BookTransactionHistory;
import com.batu.book_network.repositories.BookTransactionalHistoryRepository;
import com.batu.book_network.services.BookService;
import com.batu.book_network.services.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.batu.book_network.entites.User;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static com.batu.book_network.config.specs.BookSpecification.*;
import static com.batu.book_network.config.specs.BookTransactionalHistorySpecification.*;
import static com.batu.book_network.utils.Const.BOOK_NOT_FOUND;
import static com.batu.book_network.utils.Const.CREATED_DATE;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionalHistoryRepository historyRepository;
    private final BookTransactionalHistoryRepository bookTransactionalHistoryRepository;
    private final FileStorageService fileStorageService;

    // Admin specific
    public SaveBookResponse save(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        var book = bookMapper.requestToBook(request);
        book.setOwner(user);
        return bookMapper.toSaveBookResponse(bookRepository.save(book));
    }

    public FindBookByIdResponse findBookById(Long id) {
        BookResponse bookResponse = bookRepository.findById(id)
                .map(bookMapper::bookToResponse)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND + id));
        return bookMapper.toFindBookByIdResponse(bookResponse);
    }

    public FindAllDisplayableBooksResponse findAllDisplayableBooks(FindAllBooksRequest request, Authentication connectedUser){
        User user = (User)connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(CREATED_DATE).descending());
        Page<Book> books = bookRepository.findAll(withDisplayable(user.getId()), pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::bookToResponse)
                .toList();
        return bookMapper.toFindAllDisplayableBooksResponse(PageResponse.from( new PageImpl<>(bookResponse, pageable, books.getTotalElements())));
    }

    public FindAllBooksResponse findAllBooks(FindAllBooksRequest request){
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(CREATED_DATE).descending());
        Page<Book> books = bookRepository.findAll(pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::bookToResponse)
                .toList();
        return bookMapper.toFindAllBooksResponse(PageResponse.from( new PageImpl<>(bookResponse, pageable, books.getTotalElements())));
    }

    public FindAllBooksByOwnerResponse findAllBooksByOwner(FindAllBooksByOwnerRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(CREATED_DATE).descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);
        List<BookResponse> booksResponse = books.stream()
                .map(bookMapper::bookToResponse)
                .toList();
        return bookMapper.toFindAllBooksByOwnerResponse(PageResponse.from(new PageImpl<>(booksResponse, pageable, books.getTotalElements())));
    }

    public FindAllBorrowedBooksResponse findAllBorrowedBooks(FindAllBorrowedBooksRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(CREATED_DATE));
        Page<BookTransactionHistory> borrowedBookHistories = historyRepository.findAll(withBorrowed(user.getId()), pageable);
        List<BorrowedBookResponse> borrowedBooksResponse = borrowedBookHistories.stream()
                .map(bookMapper::historyToBorrowedBookResponse)
                .toList();
        return bookMapper.toFindAllBorrowedBooksResponse(PageResponse.from(new PageImpl<>(borrowedBooksResponse, pageable, borrowedBookHistories.getTotalElements())));
    }

    public FindAllReturnedBooksResponse findAllReturnedBooks(FindAllReturnedBooksRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(CREATED_DATE).descending());
        Page<BookTransactionHistory> returnedBooksHistories = bookTransactionalHistoryRepository.findAll(isReturned(user.getId()), pageable);
        List<BorrowedBookResponse> borrowedBookResponses = returnedBooksHistories.stream()
                .map(bookMapper::historyToBorrowedBookResponse)
                .toList();
        return bookMapper.toFindAllReturnedBooksResponse(PageResponse.from(new PageImpl<>(borrowedBookResponses, pageable, returnedBooksHistories.getTotalElements())));
    }

    public UpdateShareableStatusResponse updateShareableStatus(UpdateShareableStatusRequest request) {
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        User user = (User) request.getConnectedUser().getPrincipal();
        if (!Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("This book is not owned bu this user!");
        }
        book.setShareable(!book.isShareable());
        return bookMapper.toUpdateShareableStatusResponse(bookRepository.save(book));
    }

    public UpdateArchivedStatusResponse updateArchivedStatus(UpdateArchivedStatusRequest request){
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        User user = (User) request.getConnectedUser().getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("This book is not owned by this user!");
        }
        book.setArchived(!book.isArchived());
        return bookMapper.toUpdatedArchivedStatusResponse(bookRepository.save(book));
    }

    public BorrowBookResponse borrowBook(BorrowBookRequest request){
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        User user = checkForBorrowBook(request, book);
        BookTransactionHistory history = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();
        BookTransactionHistory bookTransactionHistory = bookTransactionalHistoryRepository.save(history);
        return bookMapper.toBorrowBookResponse(bookTransactionHistory);
    }

    public ReturnBorrowedBookResponse returnBorrowedBook(ReturnBorrowedBookRequest request){
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
        User user = (User) request.getConnectedUser().getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can't return or borrow your own book!");
        }
        BookTransactionHistory history = bookTransactionalHistoryRepository.findByBookIdAndUserId(request.getBookId(), user.getId())
                .orElseThrow( () -> new OperationNotPermittedException("You didn't borrowed this book!"));
        history.setReturned(true);
        return bookMapper.toReturnBorrowedBookResponse(bookTransactionalHistoryRepository.save(history));
    }

    public ApproveReturnBorrowedBookResponse approveReturnBorrowedBook(ApproveReturnBorrowedBookRequest request){
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        if(book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("The requested book is archived or not shareable!");
        }
        User user = (User) request.getConnectedUser().getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
        throw new OperationNotPermittedException("You can't approve the return of the book!");
        }
        BookTransactionHistory history = bookTransactionalHistoryRepository.findByBookIdAndOwnerId(request.getBookId(), user.getId())
                .orElseThrow( () -> new OperationNotPermittedException("The book is not returned yet!"));
        history.setReturnApproved(true);
        var bookTransactionHistory =  bookTransactionalHistoryRepository.save(history);
        return bookMapper.toApproveReturnBorrowedBookResponse(bookTransactionHistory);
    }

    public UploadBookCoverPictureResponse uploadBookCoverPicture(UploadBookCoverPictureRequest request) {
        var book = bookRepository.findById(request.getBookId())
                .orElseThrow( () -> new EntityNotFoundException(BOOK_NOT_FOUND + request.getBookId()));
        if(book.isArchived() || !book.isShareable()){
            throw new OperationNotPermittedException("The book is archived or not shareable at the moment!");
        }
        final String bookCoverPic = fileStorageService.saveFile(request.getFile(), request.getUserId());
        book.setBookCover(bookCoverPic);
        bookRepository.save(book);
        return bookMapper.bookCoverPictureResponse(book);
    }

    private User checkForBorrowBook(BorrowBookRequest request, Book book) {
        if(book.isArchived()){
            throw new OperationNotPermittedException("Book is archived can not be reachable at the moment!");
        } else if (!book.isShareable()) {
            throw new OperationNotPermittedException("Book is not shareable can not be reachable at the moment!");
        }
        User user = (User) request.getConnectedUser().getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can't borrow your own book!");
        }
        final boolean isAlreadyBorrowedByUser = bookTransactionalHistoryRepository.exists(isAlreadyBorrowedByUser(request.getBookId(), user.getId()));
        if(isAlreadyBorrowedByUser){
            throw new OperationNotPermittedException("You already borrowed this book and it is still not returned or the return is not approved by the owner");
        }
        final boolean isAlreadyBorrowedByOtherUser = bookTransactionalHistoryRepository.exists(isAlreadyBorrowedByAnotherUser(request.getBookId(), user.getId()));
        if(isAlreadyBorrowedByOtherUser){
            throw new OperationNotPermittedException("This book is already borrowed by another user! Wait until returned!");
        }
        return user;
    }
}
