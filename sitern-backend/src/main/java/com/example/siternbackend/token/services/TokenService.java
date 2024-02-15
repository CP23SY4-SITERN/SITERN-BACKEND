package com.example.siternbackend.token.services;

import com.example.siternbackend.token.entities.Token;
import com.example.siternbackend.token.repositories.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    public List<Token> findAll() {
        return tokenRepository.findAll();
    }

    public Optional<Token> findAllById(int id) {
        return tokenRepository.findById(id);
    }

    public Token findTokenByToken(String token) throws Exception {
        Optional<Token> tokenOptional = tokenRepository.findTokenByToken(token);
        if (tokenOptional.isPresent()) {
            return tokenOptional.get();
        } else {
            log.error("Token not found with String: " + token);
            throw new Exception("Token not found with String:" + token);
        }
    }

    public Token upsertToken(Token token) {
        return tokenRepository.save(token);
    }

    public String removeTokenById(int id) {
        try {
            Optional<Token> tokenOptional = tokenRepository.findById(id);

            if (tokenOptional.isPresent()) {
                tokenRepository.deleteById(id);
                return "Token removed successfully";
            } else {
                return "Token not found with ID: " + id;
            }
        } catch (Exception e) {
            return "An error occurred: " + e.getMessage();
        }
    }
}
