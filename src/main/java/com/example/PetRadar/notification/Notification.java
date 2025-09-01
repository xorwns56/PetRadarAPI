package com.example.PetRadar.notification;

import com.example.PetRadar.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User 엔티티와의 N:1(다대일) 연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // DB 컬럼명을 지정
    private User user;

    @Column(nullable = false)
    private String postType;

    @Column(nullable = false)
    private Long postId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
