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
        public static final String SESSION_NOT_FOUND = "exception.session.not.found";
        public static final String ERR_CREATE_SESSION = "error.create.attendance.session";
            public static final String ERR_UPDATE_SESSION = "error.update.attendance.session";
        public static final String ERR_DELETE_SESSION = "error.delete.attendance.session";
        public static final String ERR_GET_ALL_SESSION = "exception.not.get.all.session";
    }

    public static class AttendanceRecord {
        public static final String RECORD_NOT_FOUND = "exception.record.not.found";
        public static final String ERR_CHECKIN = "error.check-in.attendance.record";
        public static final String ERR_GET_BY_SESSION = "error.attendance.record.get.by.session";
        public static final String ERR_GET_BY_USER = "error.attendance.record.get.by.user";
        public static final String OUT_OF_RANGE = "error.user.out.of.range"; // thêm mới

    }

    public static class Location {
        public static final String LOCATION_NOT_FOUND = "exception.location.not.found";
        public static final String ERR_CREATE_LOCATION = "error.create.location";
        public static final String ERR_UPDATE_LOCATION = "error.update.location";
        public static final String ERR_DELETE_LOCATION = "error.delete.location";
        public static final String ERR_GET_ALL_LOCATION = "exception.not.get.all.location";
    }


}
