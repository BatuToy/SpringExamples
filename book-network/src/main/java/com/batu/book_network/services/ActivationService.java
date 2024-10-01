package com.batu.book_network.services;


import com.batu.book_network.entites.Activation;

public interface ActivationService {
    Activation save(Activation activation);
    Activation findByActivationCode(String activationCode);
    void deleteActivationCode(Activation activation);
}
