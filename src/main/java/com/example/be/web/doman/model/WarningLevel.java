package com.example.be.web.doman.model;

/**
 * Enum biểu diễn mức cảnh báo nghỉ học.
 *
 * <ul>
 *     <li>{@link #NONE} – Chưa cảnh báo.</li>
 *     <li>{@link #WARNING} – Cảnh báo lần 1.</li>
 *     <li>{@link #SERIOUS} – Cảnh báo nghiêm trọng.</li>
 *     <li>{@link #BANNED} – Cấm thi / đình chỉ.</li>
 * </ul>
 */
public enum WarningLevel {
    NONE,
    WARNING,
    SERIOUS,
    BANNED
}
