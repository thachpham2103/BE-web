package com.example.be.web.doman.model;

/**
 * Enum biểu diễn trạng thái của bài tập (Assignment).
 *
 * <ul>
 *     <li>{@link #DRAFT} – Bản nháp, chưa công khai cho sinh viên.</li>
 *     <li>{@link #PUBLISHED} – Đã công khai, sinh viên có thể xem và nộp bài.</li>
 *     <li>{@link #CLOSED} – Đã đóng, không nhận bài nộp mới.</li>
 *     <li>{@link #DELETED} – Đã bị xóa mềm.</li>
 * </ul>
 */
public enum AssignmentStatus {
    DRAFT,
    PUBLISHED,
    CLOSED,
    DELETED
}
