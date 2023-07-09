package com.goodday.proj.aop;

import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.exhandler.customex.LoginException;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.exhandler.customex.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class Authenticate {

    private final MemberRepository memberRepository;

    /**
     * 로그인 체크 AOP
     */
    @Before("execution(* com.goodday.proj.api.*.controller..*(..)) " +
            "&& !execution(* com.goodday.proj.api.member..*(..)) " +
            "&& !execution(* com.goodday.proj.api.mail..*(..)) " +
            "&& !execution(* com.goodday.proj.api.calorie..*(..)) " +
            "&& !execution(* com.goodday.proj.api.file..*(..)) ")
    public void loginAuthAOP() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Optional<String> optionalMemberNo = Optional.ofNullable(request.getHeader("memberNo"));

        if (optionalMemberNo.isEmpty()) throw new LoginException(ErrorConst.authError2);
    }

    /**
     * ADMIN 체크 AOP
     */
    @Before("execution(* com.goodday.proj.api.admin..*(..)) " +
            "&& @annotation(com.goodday.proj.annotation.AuthChecker)")
    public void adminAuthAOP() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Optional<String> optionalMemberNo = Optional.ofNullable(request.getHeader("memberNo"));

        if (memberRepository.findSessionMemberByNo(Long.valueOf(optionalMemberNo.get())).get().getAdmin().equals("N")) {
            throw new NotFoundException();
        }
    }

}
