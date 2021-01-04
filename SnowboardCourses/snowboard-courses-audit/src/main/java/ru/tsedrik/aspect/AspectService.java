package ru.tsedrik.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.aspect.annotation.Audit;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Aspect для логирования изменений в системе
 */
@Aspect
@Component
public class AspectService {

    private static final Logger logger = LoggerFactory.getLogger(AspectService.class.getName());

    private AuditMessage auditMessage;

    @Pointcut("@annotation(ru.tsedrik.aspect.annotation.Audit) && execution(public * *(..))")
    public void publicAspectMethod() {
    }

    @Around("publicAspectMethod()")
    public Object aspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        auditMessage = new AuditMessage();
        auditMessage.setAuditCode(((MethodSignature) proceedingJoinPoint.getSignature())
                .getMethod().getAnnotation(Audit.class).value());
        auditMessage.setAuditMessageEvent(AuditMessageEvent.START);
        auditMessage.setStartTime(LocalDateTime.now());
        Object[] args = Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(o -> !(o instanceof UriComponentsBuilder)).toArray();
        auditMessage.setParams(args);

        logger.info(auditMessage.toString());

        Object proceed = proceedingJoinPoint.proceed();

        auditMessage.setAuditMessageEvent(AuditMessageEvent.SUCCESS);
        auditMessage.setEndTime(LocalDateTime.now());
        auditMessage.setResult(proceed);

        logger.info(auditMessage.toString());

        return proceed;
    }

    @AfterThrowing(value = "publicAspectMethod()", throwing = "e")
    public void aspectAfterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {

        auditMessage.setAuditMessageEvent(AuditMessageEvent.FAILURE);
        auditMessage.setEndTime(LocalDateTime.now());
        auditMessage.setResult(e.getClass()+ ": " + e.getMessage());

        logger.info(auditMessage.toString());

    }

}
