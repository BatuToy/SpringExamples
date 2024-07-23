package com.batu.book_network.impl;

import com.batu.book_network.entites.Feedback;
import com.batu.book_network.repositories.FeedbackRepository;
import com.batu.book_network.services.FeedbackService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.batu.book_network.entites.Book;
import com.batu.book_network.repositories.BookRepository;
import com.batu.book_network.common.PageResponse;
import com.batu.book_network.convert.BookMapper;
import com.batu.book_network.exception.OperationNotPermittedException;
import com.batu.book_network.request.FeedbackRequest;
import com.batu.book_network.request.FindAllFeedbacksByBookIdRequest;
import com.batu.book_network.response.FeedBackResponse;
import com.batu.book_network.response.FindAllFeedbacksByBookIdResponse;
import com.batu.book_network.response.SaveFeedBackResponse;
import com.batu.book_network.entites.User;
import com.batu.book_network.utils.Const;

import jakarta.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class FeedBackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public SaveFeedBackResponse save(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
            .orElseThrow( () -> new EntityNotFoundException(Const.BOOK_NOT_FOUND + request.bookId()));
        if(book.isArchived()){
            throw new OperationNotPermittedException("Book was archived!");
        } else if(!book.isShareable()) {
            throw new OperationNotPermittedException("Book is not shareable!");
        }
        User user = (User)connectedUser.getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You cant make a feedaback to your own book!");
        }
        Feedback feedback = bookMapper.feedbackRequestToFeedBack(request);
        feedbackRepository.save(feedback);
        return bookMapper.toSaveFeedBackResponse(feedback);
    }

    public FindAllFeedbacksByBookIdResponse findAllFeedbacksByBookId(FindAllFeedbacksByBookIdRequest request, Authentication connectedUser, Long bookId) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        // page objects
        Page<Feedback> feedbacks = feedbackRepository.findAllFeedbacksByBookId(bookId, pageable); 
        User user = (User)connectedUser.getPrincipal();
        List<FeedBackResponse> feedBackResponses = feedbacks.stream().map(f -> bookMapper.toFeedBackResponse(f, user.getId())).toList();
        return bookMapper.toFindAllFeedbacksByBookIdResponse(PageResponse.from(new PageImpl<>(feedBackResponses, pageable, feedbacks.getTotalElements())));
    }
}
