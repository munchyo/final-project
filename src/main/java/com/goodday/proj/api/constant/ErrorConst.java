package com.goodday.proj.api.constant;

public abstract class ErrorConst {
    public static final String loginError = "아이디 혹은 비밀번호가 일치하지 않습니다.";

    public static final String loginErrorV2 = "로그인에 실패하였습니다.";

    public static final String registerError = "회원가입에 실패 하였습니다.";

    public static final String mailError = "유효한 이메일이 아닙니다.";

    public static final String findError = "일치하는 정보가 없습니다.";

    public static final String nullError = "값이 비어있습니다.";

    public static final String bindingError = "값이 유효하지 않습니다.";

    public static final String updateError = "업데이트 실패하였습니다.";

    public static final String insertError = "추가 실패하였습니다.";

    public static final String deleteError = "삭제 실패하였습니다.";

    public static final String authError = "권한이 없습니다.";

}
