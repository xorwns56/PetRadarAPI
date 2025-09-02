package com.example.PetRadar.notification;

import com.example.PetRadar.missing.MissingDTO;
import com.example.PetRadar.sse.SseRepository;
import com.example.PetRadar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SseRepository sseRepository;

    public void createNotification(Long userId, String postType, Long postId) {
        Notification notification = new Notification();
        notification.setUser(userRepository.getReferenceById(userId));
        notification.setPostType(postType);
        notification.setPostId(postId);

        //여기서 sendNewPostNotification 추가

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

    public void sendNewPostNotification(Long userId, String message) {
        SseEmitter emitter = sseRepository.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("newPost")
                        .data(message));
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseRepository.delete(userId);
            }
        }
    }
}
