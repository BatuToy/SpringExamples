package com.batu.book_network.convert;

import com.batu.book_network.common.PageResponse;
import com.batu.book_network.request.*;
import com.batu.book_network.response.*;
import com.batu.book_network.book.*;
import com.batu.book_network.history.BookTransactionHistory;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BookMapper {

    private final ModelMapper mapper;

    public BookMapper(ModelMapper mapper){
        this.mapper = mapper;
        bookConfig();
    }

    public void bookConfig(){

        mapper.typeMap(Book.class, BookResponse.class).addMappings(mapper -> {
            mapper.map(src -> src.getOwner().getFullName(), BookResponse:: setOwner);
            mapper.map(Book::getRate, BookResponse::setRate);
            mapper.map(Book::getBookCoverPic, BookResponse::setCover);
        });

        mapper.typeMap(BookTransactionHistory.class, BorrowedBookResponse.class).addMappings(mapper ->{
            mapper.map(src -> src.getBook().getTitle(), BorrowedBookResponse::setTitle);
            mapper.map(src -> src.getBook().getAuthorName(), BorrowedBookResponse::setAuthorName);
            mapper.map(src -> src.getBook().getIsbn(), BorrowedBookResponse::setIsbn);
            mapper.map(src -> src.getBook().getRate(), BorrowedBookResponse::setRate);
        });

        mapper.typeMap(Book.class, UploadBookCoverPictureResponse.class).addMappings(modelMapper -> {
            modelMapper.map(src -> src.getOwner().getFullName(), UploadBookCoverPictureResponse::setOwner);
            modelMapper.map(Book::getRate, UploadBookCoverPictureResponse::setRate);
            modelMapper.map(Book::getBookCoverPic, UploadBookCoverPictureResponse::setCover);
        });

        mapper.typeMap(BookTransactionHistory.class, BorrowBookResponse.class).addMappings( bookMapper -> {
            bookMapper.map(BookTransactionHistory::getId, BorrowBookResponse::setTransactionHistoryId);
        });

        mapper.typeMap(BookTransactionHistory.class, ApproveReturnBorrowedBookResponse.class).addMappings( bookMapper -> {
            bookMapper.map(BookTransactionHistory::getId, ApproveReturnBorrowedBookResponse::setTransactionHistoryId);
        });

        mapper.typeMap(Book.class, SaveBookResponse.class).addMappings( bookMapper -> {
            bookMapper.map(Book::getId, SaveBookResponse::setSaveBookId);
        });

        mapper.typeMap(BookTransactionHistory.class, ReturnBorrowedBookResponse.class).addMappings( bookMapper -> {
            bookMapper.map(BookTransactionHistory::getId, ReturnBorrowedBookResponse::setTransactionHistoryId);
        });

        mapper.typeMap(Book.class, UpdateShareableStatusResponse.class).addMappings( bookMapper ->{
            bookMapper.map(Book::getId, UpdateShareableStatusResponse::setBookId);
        });

        mapper.typeMap(Book.class, UpdateArchivedStatusResponse.class).addMappings(bookMapper ->{
            bookMapper.map(Book::getId, UpdateArchivedStatusResponse::setBookId);
        });
    }


    public UploadBookCoverPictureRequest toUploadBookCoverPictureRequest(Long bookId,
                                                                         Authentication connectedUser,
                                                                         MultipartFile file) {
        return UploadBookCoverPictureRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .file(file)
                .build();
    }

    public ApproveReturnBorrowedBookRequest toApproveReturnBorrowedBookRequest(Long bookId,
                                                                               Authentication connectedUser) {
        return ApproveReturnBorrowedBookRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .build();
    }

    public ReturnBorrowedBookRequest toReturnBorrowedBook(Long bookId,
                                                          Authentication connectedUser) {
        return ReturnBorrowedBookRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .build();
    }

    public BorrowBookRequest toBorrowBookRequest(Long bookId, Authentication connectedUser) {
        return BorrowBookRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .build();
    }

    public UpdateShareableStatusRequest toUpdateShareableStatusRequest(Long bookId, Authentication connectedUser) {
        return UpdateShareableStatusRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .build();
    }

    public UpdateArchivedStatusRequest toUpdateArchivedStatusRequest(Long bookId, Authentication connectedUser) {
        return UpdateArchivedStatusRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .build();
    }

    public FindBookByIdRequest toFindBookByIdRequest(Long bookId){
        return FindBookByIdRequest
                .builder()
                .bookId(bookId)
                .build();
    }

    public FindAllBooksRequest toFindAllBooksRequest(int page, int size, Authentication connectedUser) {
        return FindAllBooksRequest
                .builder()
                .page(page)
                .size(size)
                .connectedUser(connectedUser)
                .build();
    }

    public SaveBookRequest toSaveBookRequest(BookRequest request, Authentication connectedUser) {
        return SaveBookRequest
                .builder()
                .bookRequest(request)
                .connectedUser(connectedUser)
                .build();
    }

    public Book requestToBook(BookRequest request){
        return mapper.map(request, Book.class);

    }


    public UploadBookCoverPictureResponse bookCoverPictureResponse(Book book) {
        return mapper.map(book, UploadBookCoverPictureResponse.class);
    }

    public BookResponse bookToResponse(Book book){
        return mapper.map(book, BookResponse.class);

    }

    public BorrowedBookResponse historyToBorrowedBookResponse(BookTransactionHistory history) {
        return mapper.map(history, BorrowedBookResponse.class);

    }

    public ApproveReturnBorrowedBookResponse toApproveReturnBorrowedBookResponse(BookTransactionHistory history){
        return mapper.map(history, ApproveReturnBorrowedBookResponse.class);
    }

    public BorrowBookResponse toBorrowBookResponse(BookTransactionHistory history) {
        return mapper.map(history, BorrowBookResponse.class);
    }

    public SaveBookResponse toSaveBookResponse(Book book) {
        return mapper.map(book, SaveBookResponse.class);
    }

    public ReturnBorrowedBookResponse toReturnBorrowedBookResponse(BookTransactionHistory history) {
        return mapper.map(history, ReturnBorrowedBookResponse.class);
    }

    public UpdateShareableStatusResponse toUpdateShareableStatusResponse(Book book) {
        return mapper.map(book, UpdateShareableStatusResponse.class);
    }

    public UpdateArchivedStatusResponse toUpdatedArchivedStatusResponse(Book book) {
        return mapper.map(book, UpdateArchivedStatusResponse.class);
    }

    public FindBookByIdResponse toFindBookByIdResponse(BookResponse bookResponse) {
        return mapper.map(bookResponse, FindBookByIdResponse.class);
    }

    public FindAllBooksResponse toFindAllBooksResponse(PageResponse pageResponse) {
        return FindAllBooksResponse
                .builder()
                .pageResponse(pageResponse)
                .build();
    }

}

