package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="notifications")

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notif_id")
    private Long notifId;

    @Column(name="title", nullable = false, length = 200)
    private String title;

    @Column(name="body", nullable = false)
    private String body;

    @Column(name="is_read", nullable = false)
    private boolean isRead=false;

    @Column(name="create_at", nullable = false)
    private LocalDateTime createAt =LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;



}
