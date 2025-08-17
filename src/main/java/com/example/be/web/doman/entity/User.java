package com.example.be.web.doman.entity;

import com.example.be.web.doman.entity.common.DateAuditing;
import com.example.be.web.doman.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
@Entity

public class User extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false,insertable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Nationalized
    @Column(name="full_name")
    private String fullName;

    @Column(name="avatar_url")
    private String avatarUrl;

    @Column(name="email", unique = true, nullable = false)
    private String eamil;

    @Column(name="gender")
    private String gender;

    @Column(name="birthday")
    private LocalDate birthday;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private Role role;











}
