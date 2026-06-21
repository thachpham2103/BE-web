-- ============================================================
-- MODULE: ASSIGNMENT
-- DDL cho MySQL – Bài tập và Bài nộp
-- ============================================================

-- 1. Bảng assignments
CREATE TABLE IF NOT EXISTS `assignments` (
    `assignment_id`      BIGINT          NOT NULL AUTO_INCREMENT,
    `class_id`           BIGINT          NOT NULL,
    `title`              VARCHAR(300)    NOT NULL,
    `description`        TEXT            NULL,
    `deadline`           DATETIME        NULL,
    `max_score`          DOUBLE          NULL,
    `allow_late_submit`  TINYINT(1)      NOT NULL DEFAULT 0,
    `created_by`         BIGINT          NOT NULL,
    `status`             VARCHAR(20)     NOT NULL DEFAULT 'DRAFT',
    `create_date`        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`assignment_id`),
    CONSTRAINT `FK_ASSIGNMENT_CLASS`   FOREIGN KEY (`class_id`)    REFERENCES `class` (`class_id`),
    CONSTRAINT `FK_ASSIGNMENT_CREATOR` FOREIGN KEY (`created_by`)  REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Index cho tìm kiếm theo lớp và trạng thái
CREATE INDEX `IDX_ASSIGNMENT_CLASS_STATUS` ON `assignments` (`class_id`, `status`);

-- 2. Bảng assignment_submissions
CREATE TABLE IF NOT EXISTS `assignment_submissions` (
    `submission_id`      BIGINT          NOT NULL AUTO_INCREMENT,
    `assignment_id`      BIGINT          NOT NULL,
    `student_id`         BIGINT          NOT NULL,
    `content`            TEXT            NULL,
    `file_url`           VARCHAR(500)    NULL,
    `submitted_at`       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status`             VARCHAR(20)     NOT NULL DEFAULT 'SUBMITTED',
    `score`              DOUBLE          NULL,
    `teacher_comment`    TEXT            NULL,
    `graded_by`          BIGINT          NULL,
    `graded_at`          DATETIME        NULL,
    PRIMARY KEY (`submission_id`),
    CONSTRAINT `UK_SUBMISSION_ASSIGNMENT_STUDENT` UNIQUE (`assignment_id`, `student_id`),
    CONSTRAINT `FK_SUBMISSION_ASSIGNMENT` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`assignment_id`),
    CONSTRAINT `FK_SUBMISSION_STUDENT`    FOREIGN KEY (`student_id`)    REFERENCES `users` (`id`),
    CONSTRAINT `FK_SUBMISSION_GRADER`     FOREIGN KEY (`graded_by`)     REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Index cho tìm kiếm theo sinh viên
CREATE INDEX `IDX_SUBMISSION_STUDENT` ON `assignment_submissions` (`student_id`);
