package com.batu.book_network.request;


import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadBookCoverPictureRequest {
    private MultipartFile file;
    private Long bookId;
}
