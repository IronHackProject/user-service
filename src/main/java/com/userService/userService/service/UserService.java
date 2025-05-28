package com.userService.userService.service;

import com.userService.userService.dto.CreateUserRequestDTO;
import com.userService.userService.dto.FindUserByEmailRequestDTO;
import com.userService.userService.exceptions.customExceptions.UserExceptions;
import com.userService.userService.model.User;
import com.userService.userService.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }





    public ResponseEntity<?> createUser(CreateUserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
         throw new UserExceptions("This email is already registered");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setSurname(dto.getSurname());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    public ResponseEntity<User> findUserByEmail(FindUserByEmailRequestDTO dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty()) {
            throw new UserExceptions("User not found with email: " + dto.getEmail());
        }
        return ResponseEntity.ok(user.get());
    }

    public ResponseEntity<User> findUserByEmailOrderService(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> updateUser(Long id, @Valid CreateUserRequestDTO dto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserExceptions("User not found with id: " + id);
        }
        User user = userOptional.get();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setSurname(dto.getSurname());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<?> deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserExceptions("User not found with id: " + id);
        }
        userRepository.delete(userOptional.get());
        return ResponseEntity.ok("User deleted successfully");
    }
}
