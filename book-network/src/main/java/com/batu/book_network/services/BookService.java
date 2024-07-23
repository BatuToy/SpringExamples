package com.batu.book_network.services;

import com.batu.book_network.request.*;
import com.batu.book_network.response.*;
import org.springframework.security.core.Authentication;

public interface BookService {
    SaveBookResponse save(BookRequest request, Authentication connectedUser);
    FindBookByIdResponse findBookById(FindBookByIdRequest request);
    FindAllDisplayableBooksResponse findAllDisplayableBooks(FindAllBooksRequest request, Authentication connectedUser);
    FindAllBooksResponse findAllBooks(FindAllBooksRequest request);
    FindAllBooksByOwnerResponse findAllBooksByOwner(FindAllBooksByOwnerRequest request, Authentication connectedUser);
    FindAllBorrowedBooksResponse findAllBorrowedBooks(FindAllBorrowedBooksRequest request, Authentication connectedUser);
    FindAllReturnedBooksResponse findAllReturnedBooks(FindAllReturnedBooksRequest request, Authentication connectedUser);
    UpdateShareableStatusResponse updateShareableStatus(UpdateShareableStatusRequest request);
    UpdateArchivedStatusResponse updateArchivedStatus(UpdateArchivedStatusRequest request);
    BorrowBookResponse borrowBook(BorrowBookRequest request);
    ReturnBorrowedBookResponse returnBorrowedBook(ReturnBorrowedBookRequest request);
    ApproveReturnBorrowedBookResponse approveReturnBorrowedBook(ApproveReturnBorrowedBookRequest request);
    UploadBookCoverPictureResponse uploadBookCoverPicture(UploadBookCoverPictureRequest request, Authentication connectedUser);
}
