package com.speedhome.security;

import org.springframework.security.core.AuthenticationException;
        import org.springframework.security.web.AuthenticationEntryPoint;
        import org.springframework.stereotype.Component;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
@Component
public class MyEntryPoint implements AuthenticationEntryPoint {

    @Override
    public final void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        response.sendError(401,"You must have a valid API token");
    }
}