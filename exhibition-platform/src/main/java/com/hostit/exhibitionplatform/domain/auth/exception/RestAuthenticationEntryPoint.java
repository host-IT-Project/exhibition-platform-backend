package com.hostit.exhibitionplatform.domain.auth.exception;

import com.hostit.exhibitionplatform.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode.AUTHENTICATION_EXCEPTION;
import static com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode.EXPIRED_TOKEN;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            setResponse(response, AUTHENTICATION_EXCEPTION);
        }
        //토큰 만료된 경우
        else if (exception.equals(EXPIRED_TOKEN.getCode())) {
            setResponse(response, EXPIRED_TOKEN);
        }
    }

    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, AuthErrorCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = ErrorResponse.of(exceptionCode);
        response.getWriter().print(errorResponse.convertJson());
    }
}

