package com.sctk.cmc.common.exception;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    // Common
    SUCCESS(1000, "응답에 성공하였습니다."),
    AUTHENTICATION_ILLEGAL_EMAIL(1001, "존재하지 않는 이메일입니다."),
    AUTHENTICATION_DUPLICATE_EMAIL(1002, "중복된 이메일입니다."),
    AUTHENTICATION_ILLEGAL_PASSWORD(1003, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(1004, "서버 내부 오류 입니다."),

    // Members
    MEMBERS_ILLEGAL_ID(2001, "존재하지 않는 회원 ID입니다."),
    MEMBERS_ILLEGAL_EMAIL(2002, "존재하지 않는 회원 이메일입니다."),

    // Designers
    DESIGNERS_ILLEGAL_ID(3001, "존재하지 않는 디자이너 ID입니다."),
    DESIGNERS_HIGH_CATEGORY_MORE_THAN_LIMIT(3002, "등록할 수 있는 상위 카테고리는 최대 3개입니다."),
    DESIGNERS_LOW_CATEGORY_MORE_THAN_LIMIT(3003, "등록할 수 있는 하위 카테고리는 최대 3개입니다."),

    // jwt
    INVALID_TOKEN(4000, "잘못된 Token 입니다."),
    EXPIRED_REFRESH_TOKEN(4001, "만료된 Refresh Token 입니다."),
    INCONSISTENCY_REFRESH_TOKEN(4002, "Refresh Token 이 일치하지 않습니다."),

    // redis
    INVALID_ROLE(4003, "잘못된 ROLE 입니다.");


    private int code;
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
