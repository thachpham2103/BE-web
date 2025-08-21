package com.example.be.web.doman.entity;

import com.example.be.web.constant.ResponseMessage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_type", "target_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name = "target_type", length = 20, nullable = false)
    private String targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "score", nullable = false)
    private Short score;

    @Column(name = "rated_at", nullable = false)
    private LocalDateTime ratedAt = LocalDateTime.now();

    @PrePersist
    @PreUpdate
    private void validateScore() {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException(ResponseMessage.RATING_SCORE);
        }
    }

}
