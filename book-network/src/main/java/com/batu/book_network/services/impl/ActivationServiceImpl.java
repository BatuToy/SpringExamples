package com.batu.book_network.services.impl;

import com.batu.book_network.entites.Activation;
import com.batu.book_network.repositories.ActivationRepository;
import com.batu.book_network.services.ActivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService{

    private final ActivationRepository activationRepository;

    public Activation save(Activation activation){
        return activationRepository.save(activation);
    }

    public Activation findByActivationCode(String activationCode){
        return activationRepository.findByActivationCode(activationCode).orElseThrow(() -> new IllegalStateException("Activation not initialized!"));
    }

    public void deleteActivationCode(Activation activation){
        activationRepository.delete(activation);
    }
}
