package ru.gnekki4.loggingstarter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static ru.gnekki4.loggingstarter.utils.LoggingUtils.formatHeaders;
import static ru.gnekki4.loggingstarter.utils.LoggingUtils.formatQueryString;

@Component
public class LoggingFilter extends HttpFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var method = request.getMethod();
        var uri = formatQueryString(request);
        var headers = formatHeaders(request);

        log.info("Request: {} {} {}", method, uri, headers);

        var responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            super.doFilter(request, responseWrapper, chain);
            var body = String.format("body=%s", new String(responseWrapper.getContentAsByteArray(),
                    StandardCharsets.UTF_8));
            log.info("Response: {} {} {} {}", method, uri, response.getStatus(), body);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }
}
