package ru.gnekki4.loggingstarter.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import ru.gnekki4.loggingstarter.annotation.LogExecutionTime;

import java.util.Optional;

public class LogExecutionAspect {

    private static final Logger log = LoggerFactory.getLogger(LogExecutionAspect.class);

    @Around("@annotation(ru.gnekki4.loggingstarter.annotation.LogExecutionTime)")
    public Object aroundLogExecutionTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var start = System.currentTimeMillis();

        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();
        var originalAnnotation = method.getAnnotation(LogExecutionTime.class);
        var methodName = Optional.ofNullable(originalAnnotation)
                .map(LogExecutionTime::methodName)
                .filter(StringUtils::hasText)
                .orElse(method.getName());

        try {
            return joinPoint.proceed();
        } finally {
            log.info("Execution of '{}' took {} ms", methodName, System.currentTimeMillis() - start);
        }
    }
}
