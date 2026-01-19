package com.example.be.web.constant;

public class ErrorMessage {
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
    public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
    public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";
    public static final String INVALID_FORMAT_EMAIL="invalid.email-format";
    public static final String ROLE_NOT_FOUND="invalid.role.not-found";

    public static class User{

        public static final String USER_NOT_FOUND_ID="exception.user.not.found.id";
    }

    public static class Auth{

        public static final String INVALID_REFRESH_TOKEN = "exception.auth.invalid.refresh.token";
        public static final String ERR_INCORRECT_USERNAME = "exception.auth.incorrect.username";
    }

}
