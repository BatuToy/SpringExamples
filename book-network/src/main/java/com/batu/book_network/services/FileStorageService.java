package com.batu.book_network.services;


import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveFile(@NonNull MultipartFile file,
                    @NonNull Long userId);
}
