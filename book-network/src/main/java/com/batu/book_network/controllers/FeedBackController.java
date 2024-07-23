package com.batu.book_network.controllers;

import com.batu.book_network.impl.FeedBackServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batu.book_network.request.FeedbackRequest;
import com.batu.book_network.request.FindAllFeedbacksByBookIdRequest;
import com.batu.book_network.response.FindAllFeedbacksByBookIdResponse;
import com.batu.book_network.response.SaveFeedBackResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedbacks/")
@Tag(name = "Feedback")
public class FeedBackController {

    private final FeedBackServiceImpl service;

    @PostMapping
    public ResponseEntity<SaveFeedBackResponse> save(@RequestBody FeedbackRequest request, Authentication connectedUser){
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping(value = "book/{book-id}")
    public ResponseEntity<FindAllFeedbacksByBookIdResponse> findAllFeedbacksByBookId(
        @PathVariable("book-id") Long bookId,
        FindAllFeedbacksByBookIdRequest request,
         Authentication connectedUser){
        return ResponseEntity.ok(service.findAllFeedbacksByBookId(request, connectedUser, bookId));
    }
}
