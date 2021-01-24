package ru.tsedrik.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

/**
 * Aspect для логирования изменений в системе
 */
@Aspect
@Component
public class AspectService {

    private static final Logger logger = LoggerFactory.getLogger(AspectService.class.getName());

    @Pointcut("@annotation(ru.tsedrik.aspect.annotation.Audit) && execution(public * *(..))")
    public void publicAspectMethod() {
    }

    @Around("publicAspectMethod()")
    public Object aspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        UUID uuid = UUID.randomUUID();

        AuditMessage auditMessage = new AuditMessage();
        auditMessage.setUuid(uuid);
        auditMessage.setAuditCode(((MethodSignature) proceedingJoinPoint.getSignature())
                .getMethod().getAnnotation(Audit.class).value());
        auditMessage.setAuditMessageEvent(AuditMessageEvent.START);
        auditMessage.setStartTime(LocalDateTime.now());
        auditMessage.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Object[] args = Arrays.stream(proceedingJoinPoint.getArgs())
                .filter(o -> !(o instanceof UriComponentsBuilder)).toArray();
        auditMessage.setParams(serializeInJson(args));

        logger.info(serializeAuditMessageToString(auditMessage));

        Object proceed;

        try {
            proceed = proceedingJoinPoint.proceed();
            auditMessage.setAuditMessageEvent(AuditMessageEvent.SUCCESS);
            auditMessage.setEndTime(LocalDateTime.now());
            auditMessage.setResult(serializeInJson(proceed));

            logger.info(serializeAuditMessageToString(auditMessage));

        } catch (Exception e){
            auditMessage.setAuditMessageEvent(AuditMessageEvent.FAILURE);
            auditMessage.setEndTime(LocalDateTime.now());
            auditMessage.setResult(e.getClass()+ ": " + e.getMessage());
            logger.info(serializeAuditMessageToString(auditMessage));
            throw e;
        }

        return proceed;
    }

    private String serializeInJson(Object o){
        ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String serializedMessage = "";

        try {
            serializedMessage = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return serializedMessage;
    }

    public static String serializeAuditMessageToString(AuditMessage auditMessage){

        String result = String.join(";",
                auditMessage.getUuid().toString(),
                auditMessage.getAuditCode().name(),
                auditMessage.getAuditMessageEvent().name(),
                auditMessage.getStartTime() == null ? "" : auditMessage.getStartTime().toString(),
                auditMessage.getEndTime() == null ? "" : auditMessage.getEndTime().toString(),
                auditMessage.getUserName(),
                auditMessage.getParams(),
                auditMessage.getResult());

        return result;
    }

    public static AuditMessage deserializeAuditMessageFromString(String msg){
        AuditMessage auditMessage = new AuditMessage();

        String []  data = msg.split(";");

        int counter = 0;
        auditMessage.setUuid(UUID.fromString(data[counter++]));
        auditMessage.setAuditCode(AuditCode.valueOf(data[counter++]));
        auditMessage.setAuditMessageEvent(AuditMessageEvent.valueOf(data[counter++]));

        String startTime = data[counter++];
        if (!startTime.isEmpty()) {
            auditMessage.setStartTime(LocalDateTime.parse(startTime));
        }

        String endTime = data[counter++];
        if (!endTime.isEmpty()) {
            auditMessage.setStartTime(LocalDateTime.parse(endTime));
        }

        auditMessage.setUserName(data[counter++]);
        auditMessage.setParams(data[counter++]);
        auditMessage.setResult(data[counter++]);

        return auditMessage;
    }

}
