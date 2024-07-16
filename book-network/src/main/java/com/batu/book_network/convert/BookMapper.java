package com.batu.book_network.convert;

import com.batu.book_network.request.ApproveReturnBorrowedBookRequest;
import com.batu.book_network.request.BookRequest;
import com.batu.book_network.request.ReturnBorrowedBookRequest;
import com.batu.book_network.request.UploadBookCoverPictureRequest;
import com.batu.book_network.response.BookResponse;
import com.batu.book_network.response.BorrowedBookResponse;
import com.batu.book_network.response.UploadBookCoverPictureResponse;
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
    }

    public Book requestToBook(BookRequest request){
        return mapper.map(request, Book.class);

    }

    public BookResponse bookToResponse(Book book){
        // Check the book, see if there is need for a config to correct the mapping.
        return mapper.map(book, BookResponse.class);

    }

    public BorrowedBookResponse historyToBorrowedBookResponse(BookTransactionHistory history) {
        return mapper.map(history, BorrowedBookResponse.class);

    }

    public UploadBookCoverPictureResponse bookCoverPictureResponse(Book book) {
        return mapper.map(book, UploadBookCoverPictureResponse.class);
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

    public ReturnBorrowedBookRequest toReturnBorrowedBook(Long bookId, Authentication connectedUser) {
        return ReturnBorrowedBookRequest
                .builder()
                .bookId(bookId)
                .connectedUser(connectedUser)
                .build();
    }
}
