-- ============================================================
-- MODULE: ATTENDANCE ADVANCED
-- DDL cho MySQL – Chính sách, Cảnh báo, Giải trình
-- ============================================================

-- 1. Bảng attendance_policies (1-1 với class)
CREATE TABLE IF NOT EXISTS `attendance_policies` (
    `policy_id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `class_id`           BIGINT      NOT NULL,
    `warning_threshold`  INT         NOT NULL DEFAULT 3,
    `ban_threshold`      INT         NOT NULL DEFAULT 5,
    `allow_late_minutes` INT         NOT NULL DEFAULT 15,
    `create_date`        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`policy_id`),
    CONSTRAINT `UK_POLICY_CLASS`  UNIQUE (`class_id`),
    CONSTRAINT `FK_POLICY_CLASS`  FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Bảng attendance_warnings
CREATE TABLE IF NOT EXISTS `attendance_warnings` (
    `warning_id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `student_id`         BIGINT      NOT NULL,
    `class_id`           BIGINT      NOT NULL,
    `absent_count`       INT         NOT NULL,
    `total_session`      INT         NOT NULL,
    `absent_rate`        DOUBLE      NOT NULL,
    `warning_level`      VARCHAR(20) NOT NULL,
    `message`            TEXT        NULL,
    `status`             VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    `create_date`        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`warning_id`),
    CONSTRAINT `FK_WARNING_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK_WARNING_CLASS`   FOREIGN KEY (`class_id`)   REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX `IDX_WARNING_STUDENT_CLASS` ON `attendance_warnings` (`student_id`, `class_id`);

-- 3. Bảng attendance_appeals
CREATE TABLE IF NOT EXISTS `attendance_appeals` (
    `appeal_id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `student_id`         BIGINT       NOT NULL,
    `session_id`         BIGINT       NOT NULL,
    `record_id`          BIGINT       NOT NULL,
    `reason`             TEXT         NOT NULL,
    `proof_image_url`    VARCHAR(500) NULL,
    `status`             VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    `teacher_note`       TEXT         NULL,
    `reviewed_by`        BIGINT       NULL,
    `reviewed_at`        DATETIME     NULL,
    `create_date`        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`appeal_id`),
    CONSTRAINT `UK_APPEAL_STUDENT_RECORD` UNIQUE (`student_id`, `record_id`),
    CONSTRAINT `FK_APPEAL_STUDENT`  FOREIGN KEY (`student_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK_APPEAL_SESSION`  FOREIGN KEY (`session_id`) REFERENCES `attendance_session` (`session_id`),
    CONSTRAINT `FK_APPEAL_RECORD`   FOREIGN KEY (`record_id`)  REFERENCES `attendance_record` (`record_id`),
    CONSTRAINT `FK_APPEAL_REVIEWER` FOREIGN KEY (`reviewed_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX `IDX_APPEAL_STATUS` ON `attendance_appeals` (`status`);
