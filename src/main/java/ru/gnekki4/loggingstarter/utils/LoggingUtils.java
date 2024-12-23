package ru.gnekki4.loggingstarter.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public final class LoggingUtils {

    private LoggingUtils() {
    }

    public static String formatQueryString(HttpServletRequest request) {
        return Optional.ofNullable(request.getQueryString())
                .map(queryString -> String.format("%s?%s", request.getRequestURI(), queryString))
                .orElse("");
    }

    public static String formatHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .map(header -> String.format("%s=%s", header, request.getHeader(header)))
                .collect(Collectors.joining(","));
    }
}