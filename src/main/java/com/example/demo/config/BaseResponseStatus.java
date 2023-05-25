package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_ID(false, 2015, "아이디를 입력해주세요."),
    POST_USERS_INVALID_ID(false, 2016, "아이디 형식을 확인해주세요."),
    POST_USERS_EXISTS_ID(false,2017,"중복된 아이디입니다."),

    // [POST] /add
    USERS_EMPTY_USER_IDX(false, 2018, "유효하지 않은 유저 번호입니다."),
    EMPTY_ITEM_NAME(false, 2019, "상품제목이 비어있습니다."),
    EMPTY_ITEM_CONTENT(false, 2020, "상품설명이 비어있습니다."),
    POST_ITEM_TITLE_INVALID(false, 2021, "제목길이가 유효하지 않습니다"),
    POST_ITEM_CONTENT_INVALID(false, 2022, "설명길이가 유효하지 않습니다."),

    INVALID_ITEM_IDX(false, 2023, "상품번호가 일치하지 않습니다."),
    EMPTY_ITEM_IDX(false, 2024, "상품번호가 없습니다."),
    EMPTY_CATEGORY_IDX(false, 2025, "카테고리가 비어있습니다."),




    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_ID(false, 3013, "중복된 아이디입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 아이디가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),
    ID_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    ID_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),


    ID_ACCESS_ERROR(false, 5000, "잘못된 접근입니다.");

    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
