package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="face_data")

public class FaceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facedataId;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="create_at", nullable = false)
    private LocalDateTime createAt;

    @Lob
    @Column(name="face_encoding", nullable = false, columnDefinition = "TEXT")
    private String faceEncoding;

    @ManyToOne
    @JoinColumn(
            name="user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name="FK_USER_ID")
    )
    private User user;



}
