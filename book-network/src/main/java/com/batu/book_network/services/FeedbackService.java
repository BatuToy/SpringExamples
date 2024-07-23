package com.batu.book_network.services;

import com.batu.book_network.request.FeedbackRequest;
import com.batu.book_network.request.FindAllFeedbacksByBookIdRequest;
import com.batu.book_network.response.SaveFeedBackResponse;
import com.batu.book_network.response.FindAllFeedbacksByBookIdResponse;
import org.springframework.security.core.Authentication;

public interface FeedbackService {
    SaveFeedBackResponse save(FeedbackRequest request, Authentication connectedUser);
    FindAllFeedbacksByBookIdResponse findAllFeedbacksByBookId(FindAllFeedbacksByBookIdRequest request, Authentication connectedUser, Long bookId);
}
