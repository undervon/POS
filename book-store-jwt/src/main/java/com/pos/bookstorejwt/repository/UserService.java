package com.pos.bookstorejwt.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(
                String.format("Userul %s nu exista in DB", username)));
    }

    public void checkByUsername(String username) {
        userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(
                String.format("Userul %s nu exista in DB", username)));
    }

    public void checkPassword(String username, String password) {
        if (!bCryptPasswordEncoder.matches(password, userRepository.getByUsername(username).getPassword())) {
            throw new RuntimeException("Date de autentificare incorecte");
        }
    }

    public User createNewUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Userul deja exista in DB");
        }

        User user = User.builder()
                .username(username)
                .password(password)
//                .password(bCryptPasswordEncoder.encode(password))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }
}
