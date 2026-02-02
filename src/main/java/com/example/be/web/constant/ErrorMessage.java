package com.example.be.web.constant;

public class ErrorMessage {
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
    public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
    public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";
    public static final String INVALID_FORMAT_EMAIL="invalid.email-format";
    public static final String ROLE_NOT_FOUND="invalid.role.not-found";
    public static final String UNAUTHORIZED = "exception.unauthorized";

    public static class User{

        public static final String USER_NOT_FOUND_ID="exception.user.not.found.id";
        public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
//        public static final String ERR_NOT_FOUND_ID = "exception.user.not.found.id";
    }

    public static class Auth{

        public static final String INVALID_REFRESH_TOKEN = "exception.auth.invalid.refresh.token";
        public static final String ERR_INCORRECT_USERNAME = "exception.auth.incorrect.username";
    }

    public static class AttendanceSession {
        public static final String SESSION_NOT_FOUND = "Không tìm thấy buổi điểm danh với ID: %s";
        public static final String ERR_CREATE_SESSION = "Lỗi khi tạo buổi điểm danh";
        public static final String ERR_UPDATE_SESSION = "Lỗi khi cập nhật buổi điểm danh";
        public static final String ERR_DELETE_SESSION = "Lỗi khi xóa buổi điểm danh";
        public static final String ERR_GET_ALL_SESSION = "Lỗi khi lấy danh sách buổi điểm danh";
    }

    public static class AttendanceRecord {
        public static final String RECORD_NOT_FOUND = "Không tìm thấy bản ghi điểm danh với ID: %s";
        public static final String ERR_CHECKIN = "Lỗi khi điểm danh";
        public static final String ERR_GET_BY_SESSION = "Lỗi khi lấy bản ghi theo buổi học";
        public static final String ERR_GET_BY_USER = "Lỗi khi lấy bản ghi theo người dùng";
        public static final String OUT_OF_RANGE = "Bạn đang ngoài phạm vi điểm danh"; // thêm mới

    }


}
