package com.example.treeze.util;

public class MessageUtil {
    //success
    // User
    public static final String LOGIN_SUCCESS = "로그인에 성공하였습니다.";
    public static final String SIGNUP_SUCCESS = "회원가입에 성공하였습니다.";
    public static final String REFRESH_TOKEN_SAVE_SUCCESS = "리프레시 토큰 저장에 성공하였습니다.";

    //openai
    public static final String OPENAI_SUCCESS = "답변에 성공하였습니다.";

    //failed
    // User
    public static final String USER_NOT_EXIST = "아이디, 비밀번호를 확인해 주세요.";
    public static final String USER_ALREADY_EXIST = "이미 등록된 회원입니다.";
    public static final String TOKEN_FAILED = "토큰 생성에 실패하였습니다.";
    public static final String SIGNUP_FAILED = "회원가입에 실패하였습니다.";
    public static final String OTHER_PASSWORD = "비밀번호가 서로 다릅니다.";
    public static final String DIFF_PASSWORD = "비밀번호를 확인해 주세요.";
    public static final String INVALID_PASSWORD = "비밀번호는 대,소문자, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.";
    public static final String INVALID_PHONENUMBER = "휴대폰번호를 확인해 주세요.";
    public static final String INVALID_LENGTH_PASSWORD = "비밀번호는 최소 8자 이상, 최대 20자 이하입니다.";
    public static final String INVALID_LENGTH_ID = "아이디는 최소 4자 이상, 최대 15자 이하입니다.";
    public static final String BLANK_ID = "아이디를 입력해 주세요.";
    public static final String BLANK_PASSWORD = "비밀번호를 입력해 주세요.";
    public static final String BLANK_PASSWORD_CONFIRM = "비밀번호 확인을 입력해 주세요.";
    public static final String BLANK_PHONENUMBER = "휴대폰 번호를 입력해 주세요.";
    public static final String REFRESH_TOKEN_SAVE_FAILED = "리프레시 토큰 저장에 실패하였습니다.";

    //openai
    public static final String BLANK_PRMOPT = "질문을 입력해 주세요.";
    public static final String OPENAI_FAILED = "답변에 실패하였습니다.";





}
