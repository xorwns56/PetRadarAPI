package com.example.PetRadar.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

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

    @Transactional
    public void updateUser(Long userId, UserRegisterDTO userRegisterDTO) {
        final String HP_REGEX = "^01[0-9]{1}-\\d{3,4}-\\d{4}$";
        final String PW_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (userRegisterDTO.getHp() == null || userRegisterDTO.getHp().isBlank()) {
            throw new IllegalArgumentException("휴대폰 번호를 입력해주세요.");
        }
        if (!Pattern.matches(HP_REGEX, userRegisterDTO.getHp())) {
            throw new IllegalArgumentException("유효한 휴대폰 번호 형식이 아닙니다.");
        }
        user.setHp(userRegisterDTO.getHp());
        if (userRegisterDTO.getPw() != null && !userRegisterDTO.getPw().isEmpty()) {
            if (!Pattern.matches(PW_REGEX, userRegisterDTO.getPw())) {
                throw new IllegalArgumentException("비밀번호는 8자 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.");
            }
            user.setPwHash(passwordEncoder.encode(userRegisterDTO.getPw()));
        }
        userRepository.save(user);
    }
}
