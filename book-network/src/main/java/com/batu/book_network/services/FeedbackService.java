package com.batu.book_network.services;

import com.batu.book_network.config.request.FeedbackRequest;
import com.batu.book_network.config.request.FindAllFeedbacksByBookIdRequest;
import com.batu.book_network.config.response.SaveFeedBackResponse;
import com.batu.book_network.config.response.FindAllFeedbacksByBookIdResponse;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    SaveFeedBackResponse save(FeedbackRequest request, Authentication connectedUser);
    FindAllFeedbacksByBookIdResponse findAllFeedbacksByBookId(FindAllFeedbacksByBookIdRequest request, Authentication connectedUser, Long bookId);
}
