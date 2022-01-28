package com.pos.bookstorejwt.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(
                String.format("Userul %s nu exista in DB", username)));
    }

    public void checkPassword(String username, String password) {
        if (!userRepository.getByUsername(username).getPassword().equals(password)) {
            throw new RuntimeException("Date de autentificare incorecte");
        }

        /* Am comentat partile de cod cu criptarea/decriptarea parolei pentru a testa aplicatiei mai usor */

//        if (!bCryptPasswordEncoder.matches(password, userRepository.getByUsername(username).getPassword())) {
//            throw new RuntimeException("Date de autentificare incorecte");
//        }
    }

    public User registerNewUser(String username, String password) {
        this.checkIfUserIsPresentInDB(username);

        User user = User.builder()
                .username(username)
                .password(password)
//                .password(bCryptPasswordEncoder.encode(password))
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public User createNewUser(String username, String password, String role) {
        this.checkIfUserIsPresentInDB(username);

        User user = User.builder()
                .username(username)
                .password(password)
//                .password(bCryptPasswordEncoder.encode(password))
                .role(this.setUserRole(role))
                .build();

        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = this.findByUsername(username);

        if (user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Nu se poate sterge un user cu rol de ADMIN");
        }

        userRepository.delete(user);
    }

    public void editPasswordUser(String username, String oldPassword, String newPassword) {
        User user = this.findByUsername(username);

//        if (!bCryptPasswordEncoder.matches(user.getPassword(), oldPassword)) {
//            throw new RuntimeException("Parola veche gresita");
//        }

        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Parola veche gresita");
        }

//        if (bCryptPasswordEncoder.matches(oldPassword, bCryptPasswordEncoder.encode(newPassword))) {
//            throw new RuntimeException("Parola trebuie sa fie diferita");
//        }

        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("Parola trebuie sa fie diferita");
        }

        user.setPassword(newPassword);

        userRepository.save(user);
    }

    public void editRoleUser(String username, String role) {
        User user = this.findByUsername(username);

        if (user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Nu se poate schimba rolul unui ADMIN");
        }

        user.setRole(this.setUserRole(role));

        userRepository.save(user);
    }

    private void checkIfUserIsPresentInDB(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Userul deja exista in DB");
        }
    }

    private Role setUserRole(String role) {
        switch (role.toUpperCase(Locale.ROOT)) {
            case "USER":
                return Role.USER;
            case "ADMIN":
                return Role.ADMIN;
            case "MANAGER":
                return Role.MANAGER;
            default:
                throw new RuntimeException("Rolul nu exista");
        }
    }
}
