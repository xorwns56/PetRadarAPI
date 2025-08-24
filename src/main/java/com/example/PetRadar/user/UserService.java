package com.example.PetRadar.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDto) {
        User user = new User();
        user.setLoginId(userRegisterDto.getId());
        user.setHp(userRegisterDto.getHp());
        user.setPwHash(passwordEncoder.encode(userRegisterDto.getPw()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return userRepository.findById(id) // Optional<User>를 반환
                .map(user -> new UserDTO(user.getId(), user.getLoginId(), user.getHp())) // User를 UserDTO로 변환
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id)); // 값이 없으면 예외 발생
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }



}
