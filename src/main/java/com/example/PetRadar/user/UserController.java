package com.example.PetRadar.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user") // or /api/users (recommended)
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.ok(null);
        }
        Long userId = Long.parseLong(userDetails.getUsername());
        UserDTO userDto = userService.findById(userId);
        return ResponseEntity.ok(userDto);
    }
}
