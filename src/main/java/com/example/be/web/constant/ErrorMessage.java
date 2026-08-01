package com.example.be.web.constant;

import org.hibernate.boot.cfgxml.internal.CfgXmlAccessServiceImpl;

public class ErrorMessage {
    public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
    public static final String INVALID_FORMAT_PASSWORD = "invalid.password-format";
    public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";
    public static final String INVALID_FORMAT_EMAIL="invalid.email-format";
    public static final String ROLE_NOT_FOUND="invalid.role.not-found";
    public static final String UNAUTHORIZED = "exception.unauthorized";
    public static final String FORBIDDEN_UPDATE_DELETE = "exception.forbidden.update-delete";
    public static final String REQUEST_NOT_FOUND="exception.not.found";

    public static class User{

        public static final String USER_NOT_FOUND_ID="exception.user.not.found.id";
        public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
        public static final String ERR_NOT_FOUND = "exception.user.not.found";
        public static final  String USER_NOT_FOUND="exception.user.not.found";
    }

    public static class Blog{
        public static final String BLOG_NOT_FOUND="exception.blog.not.found";
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
        public static final String OPEN_SESSION_NOT_FOUND_FOR_TEACHER = "exception.open.session.not.found.for.teacher";
    }

    public static class AttendanceRecord {
        public static final String RECORD_NOT_FOUND = "exception.record.not.found";
        public static final String ERR_CHECKIN = "error.check-in.attendance.record";
        public static final String ERR_GET_BY_SESSION = "error.attendance.record.get.by.session";
        public static final String ERR_GET_BY_USER = "error.attendance.record.get.by.user";
        public static final String OUT_OF_RANGE = "error.user.out.of.range"; // thêm mới
        public static final String OUT_OF_TIME = "error.user.out.of.time";
        public static final String FACE_NOT_MATCH = "error.face.not.match";

    }

    public static class Location {
        public static final String LOCATION_NOT_FOUND = "exception.location.not.found";
        public static final String ERR_CREATE_LOCATION = "error.create.location";
        public static final String ERR_UPDATE_LOCATION = "error.update.location";
        public static final String ERR_DELETE_LOCATION = "error.delete.location";
        public static final String ERR_GET_ALL_LOCATION = "exception.not.get.all.location";
    }

    public static class ClassRoom {
        public static final String CLASS_NOT_FOUND = "exception.class.not.found";
        public static final String ERR_CREATE_CLASS = "error.create.class";
        public static final String ERR_UPDATE_CLASS = "error.update.class";
        public static final String ERR_DELETE_CLASS = "error.delete.class";
        public static final String ERR_GET_ALL_CLASS = "exception.not.get.all.class";
    }

    public static class ClassRegistration{
        public static final String REGISTRATION_NOT_FOUND = "exception.registration.not.found";
        public static final String ERR_CREATE_REGISTRATION = "error.create.class.registration";
        public static final String GET_MY_REGISTRATIONS_FAILED = "exception.class-registration.get_my_registrations_failed";
        public static final String ERR_DELETE_REGISTRATION = "error.delete.class.registration";
        public static final String ERR_GET_BY_CLASS = "error.class.registration.get.by.class";
        public static final String ERR_GET_BY_USER = "error.class.registration.get.by.user";
        public static final String REGISTERED = "error.student.already.registered";
    }

    public static class FaceData{
        public static final String ERR_NOT_FOUND_USERID = "exception.face-data.not.found.userId";
    }

    public static class Assignment {
        public static final String ASSIGNMENT_NOT_FOUND = "exception.assignment.not.found";
        public static final String ASSIGNMENT_NOT_PUBLISHED = "exception.assignment.not.published";
        public static final String ALREADY_SUBMITTED = "error.assignment.already.submitted";
        public static final String DEADLINE_PASSED = "error.assignment.deadline.passed";
        public static final String SCORE_EXCEEDS_MAX = "error.assignment.score.exceeds.max";
        public static final String SUBMISSION_NOT_FOUND = "exception.assignment.submission.not.found";
        public static final String SUBMISSION_ALREADY_GRADED = "error.assignment.submission.already.graded";
    }

    public static class AttendanceAdvanced {
        public static final String POLICY_NOT_FOUND = "exception.attendance.policy.not.found";
        public static final String POLICY_ALREADY_EXISTS = "error.attendance.policy.already.exists";
        public static final String BAN_MUST_GREATER_THAN_WARNING = "error.attendance.policy.ban.must.greater.than.warning";
        public static final String WARNING_NOT_FOUND = "exception.attendance.warning.not.found";
        public static final String APPEAL_NOT_FOUND = "exception.attendance.appeal.not.found";
        public static final String APPEAL_ALREADY_EXISTS = "error.attendance.appeal.already.exists";
        public static final String APPEAL_ALREADY_REVIEWED = "error.attendance.appeal.already.reviewed";
    }

    public static class Payment {
        public static final String PAYMENT_NOT_FOUND = "exception.payment.not.found";
        public static final String PAYMENT_ALREADY_EXISTS = "error.payment.already.exists";
        public static final String INVALID_STATUS_TRANSITION = "error.payment.invalid.status.transition";
        public static final String INVOICE_NOT_FOUND = "exception.invoice.not.found";
    }

//    public static class ClassRegistration {
//        public static final String REGISTRATION_NOT_FOUND = "exception.class.registration.not.found";
//    }

    public static class LearningTask{
        public static final String TASK_NOT_FOUND="exception.task.not.found";
    }

    public static class Notification{
        public static final String NOTIFICATION_NOT_FOUND="exception.notification.not.found";
    }

    public static class NotificationTemplate{
        public static final String TEMPLATE_NOT_FOUND="exception.template.not.found";
    }

    public static class Conversation{
        public static final String CONVERSATION_NOT_FOUND="exception.conversation.not.found";
    }

    public static class Message{
        public static final String MESSAGE_NOT_FOUND="exception.message.not.found";
    }


}
