package com.example.be.web.doman.model;

/**
 * Enum biểu diễn trạng thái bài nộp của sinh viên.
 *
 * <ul>
 *     <li>{@link #SUBMITTED} – Đã nộp, chờ chấm.</li>
 *     <li>{@link #LATE} – Nộp trễ sau deadline.</li>
 *     <li>{@link #GRADED} – Đã được chấm điểm.</li>
 *     <li>{@link #RETURNED} – Trả lại để sửa.</li>
 * </ul>
 */
public enum SubmissionAssignmentStatus {
    SUBMITTED,
    LATE,
    GRADED,
    RETURNED
}
