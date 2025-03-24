package org.jboss.as.quickstarts.kitchensink.filter;

import jakarta.servlet.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID = "requestId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID, requestId); // Store in MDC for logging

        if (request instanceof HttpServletRequest httpRequest) {
            log.info("Request received : uri : {}, method : {}", httpRequest.getRequestURI(),
                    httpRequest.getMethod());
        }
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID); // Clean up after request is complete
        }
    }
}

