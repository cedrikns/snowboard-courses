package ru.tsedrik.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import ru.tsedrik.resource.dto.ResponseError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Фильтр для реализации аутентификации с помощью токена
 */
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${spring.application.name}")
    private String systemName;

    public TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

        setAuthenticationSuccessHandler((request, response, auth) -> {
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.getRequestDispatcher(request.getServletPath()).forward(request, response);

        });

        setAuthenticationFailureHandler((request, response, authenticationException) -> {
            ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                    authenticationException.getMessage(),
                    "authenticationException",
                    systemName);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(responseError));
        });

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        final String authToken = Optional.ofNullable(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElseThrow(() -> new AuthenticationServiceException("Не найдены данные для авторизации"));

        Authentication authentication = getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(authToken, authToken));
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationServiceException("Токен не валиден");
        }
        return authentication;
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
