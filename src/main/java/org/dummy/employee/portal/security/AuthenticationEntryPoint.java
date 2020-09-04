package org.dummy.employee.portal.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class AuthenticationEntryPoint.
 */
@Component

/** The Constant log. */
@Slf4j
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {


    /**
     * Commence.
     *
     * @param request the request
     * @param response the response
     * @param authEx the auth ex
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException {
        log.error("Authentication error", authEx);
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authEx.getMessage());
    }

    /**
     * After properties set.
     */
    @Override
    public void afterPropertiesSet() {
        setRealmName("Application Realm");
        super.afterPropertiesSet();
    }


}
