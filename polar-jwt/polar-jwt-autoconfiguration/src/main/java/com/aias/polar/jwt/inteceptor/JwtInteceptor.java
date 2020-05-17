package com.aias.polar.jwt.inteceptor;

import com.aias.polar.common.utils.ResultUtils;
import com.aias.polar.jwt.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuhy
 * @ClassName JwtInteceptor
 * @date 2020/1/29 14:02
 */
@Order(0)
//@Component
public class JwtInteceptor extends HandlerInterceptorAdapter {

    private static final String HEADER_KEY = "Authorization";

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authToken = request.getHeader(HEADER_KEY);
        if (validateTokenString(authToken)) {
            authToken = request.getParameter(HEADER_KEY);
            if (StringUtils.isEmpty(authToken)) {
                authToken = (String) request.getSession().getAttribute(HEADER_KEY);
                if (validateTokenString(authToken)) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null && cookies.length > 0) {
                        for (Cookie cookie : cookies) {
                            String name = cookie.getName();
                            if (!HEADER_KEY.equals(name)) {
                                continue;
                            }
                            authToken = cookie.getValue();
                        }
                    }
                }
            }
        }

        if (validateTokenString(authToken)) {
            printOutResponse(response, ResultUtils.fail("need login!").toString());
            return false;
        }

        String subject = jwtUtils.getSubjectFromJwt(authToken);

        if (StringUtils.isEmpty(subject)) {
            printOutResponse(response, ResultUtils.fail("need login!").toString());
            return false;
        }

        request.setAttribute("jwtSubject", subject);

        return super.preHandle(request, response, handler);
    }

    private boolean validateTokenString(String authToken) {
        return StringUtils.isEmpty(authToken) || "undefined".equals(authToken);
    }

    private void printOutResponse(HttpServletResponse response, String msg) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(msg);
            writer.flush();
        }
    }
}
