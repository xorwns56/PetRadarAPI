package com.example.PetRadar.notification;

import com.example.PetRadar.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDTO {
    private Long id;
    private User user;
    private String postType;
    private Long postId;
}
