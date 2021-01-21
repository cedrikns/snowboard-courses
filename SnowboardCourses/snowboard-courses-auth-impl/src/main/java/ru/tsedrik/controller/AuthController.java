package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.tsedrik.aspect.annotation.Audit;
import ru.tsedrik.aspect.annotation.AuditCode;
import ru.tsedrik.resource.AuthResource;
import ru.tsedrik.resource.dto.AuthUserDto;
import ru.tsedrik.resource.dto.TokenDto;
import ru.tsedrik.service.AuthService;

/**
 * Controller for Token
 */
@RestController
public class AuthController implements AuthResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @Audit(AuditCode.TOKEN_CREATE)
    @Override
    public ResponseEntity<TokenDto> createToken(AuthUserDto authUserDto) {
        logger.debug("createToken with {} - start ", authUserDto);
        TokenDto tokenDto = authService.createToken(authUserDto);
        logger.debug("createToken end with result {}", tokenDto);
        return ResponseEntity.ok(tokenDto);
    }
}
