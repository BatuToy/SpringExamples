package com.batu.book_network.config.request;


import lombok.*;
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
