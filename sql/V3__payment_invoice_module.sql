-- ============================================================
-- MODULE: PAYMENT + INVOICE
-- DDL cho MySQL
-- ============================================================

-- 1. Bảng payments
CREATE TABLE IF NOT EXISTS `payments` (
    `payment_id`         BIGINT          NOT NULL AUTO_INCREMENT,
    `user_id`            BIGINT          NOT NULL,
    `registration_id`    BIGINT          NOT NULL,
    `amount`             DECIMAL(12, 2)  NOT NULL,
    `payment_method`     VARCHAR(20)     NOT NULL,
    `payment_status`     VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    `transaction_code`   VARCHAR(100)    NULL,
    `proof_image_url`    VARCHAR(500)    NULL,
    `paid_at`            DATETIME        NULL,
    `confirmed_by`       BIGINT          NULL,
    `confirmed_at`       DATETIME        NULL,
    `note`               TEXT            NULL,
    `create_date`        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_date` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`payment_id`),
    CONSTRAINT `UK_PAYMENT_REGISTRATION` UNIQUE (`registration_id`),
    CONSTRAINT `FK_PAYMENT_USER`         FOREIGN KEY (`user_id`)          REFERENCES `users` (`id`),
    CONSTRAINT `FK_PAYMENT_REGISTRATION` FOREIGN KEY (`registration_id`)  REFERENCES `class_registrations` (`registration_id`),
    CONSTRAINT `FK_PAYMENT_CONFIRMER`    FOREIGN KEY (`confirmed_by`)     REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX `IDX_PAYMENT_STATUS` ON `payments` (`payment_status`);
CREATE INDEX `IDX_PAYMENT_USER`   ON `payments` (`user_id`);

-- 2. Bảng invoices
CREATE TABLE IF NOT EXISTS `invoices` (
    `invoice_id`   BIGINT          NOT NULL AUTO_INCREMENT,
    `payment_id`   BIGINT          NOT NULL,
    `invoice_code` VARCHAR(50)     NOT NULL,
    `user_id`      BIGINT          NOT NULL,
    `amount`       DECIMAL(12, 2)  NOT NULL,
    `issue_date`   DATE            NOT NULL,
    `file_url`     VARCHAR(500)    NULL,
    `status`       VARCHAR(20)     NOT NULL DEFAULT 'DRAFT',
    `created_at`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`invoice_id`),
    CONSTRAINT `UK_INVOICE_PAYMENT` UNIQUE (`payment_id`),
    CONSTRAINT `UK_INVOICE_CODE`    UNIQUE (`invoice_code`),
    CONSTRAINT `FK_INVOICE_PAYMENT` FOREIGN KEY (`payment_id`) REFERENCES `payments` (`payment_id`),
    CONSTRAINT `FK_INVOICE_USER`    FOREIGN KEY (`user_id`)    REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
