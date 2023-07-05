package com.goodday.proj.exhandler.customex;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginException extends RuntimeException {
    public LoginException(String msg) {
        super(msg);
    }
}
