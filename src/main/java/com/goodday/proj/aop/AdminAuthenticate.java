package com.goodday.proj.aop;

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
public class AdminAuthenticate {

    private final MemberRepository memberRepository;

    /**
     * ADMIN 체크 AOP
     */
    @Before("execution(* com.goodday.proj.api.admin..*(..)) ")
    public void adminAuthAOP() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Optional<String> optionalMemberNo = Optional.ofNullable(request.getHeader("memberNo"));

        if (optionalMemberNo.get().equals("null") ||
                memberRepository.findSessionMemberByNo(Long.valueOf(optionalMemberNo.get())).get().getAdmin().equals("N")) {
            throw new NotFoundException();
        }
    }
}
