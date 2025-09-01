package com.example.PetRadar.notification;

import com.example.PetRadar.missing.MissingDTO;
import com.example.PetRadar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;

    public void createNotification(Long userId, String postType, Long postId) {
        Notification notification = new Notification();
        notification.setUser(userRepository.getReferenceById(userId));
        notification.setPostType(postType);
        notification.setPostId(postId);
        notificationRepository.save(notification);
    }

    public List<NotificationDTO> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream().map(
                notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getUser(),
                        notification.getPostType(),
                        notification.getPostId()
                )
        ).collect(Collectors.toList());
    }
}
