package com.example.be.web.doman.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "submission_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private ContestSubmission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id")
    private AnswerOption selectedOption;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "score")
    private Double score;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "teacher_comment", columnDefinition = "TEXT")
    private String teacherComment;

    @PrePersist
    public void prePersist() {
        if (this.score == null) {
            this.score = 0.0;
        }

        if (this.isCorrect == null) {
            this.isCorrect = false;
        }
    }
}