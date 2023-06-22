package com.pifrans.ecommerce.errors.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pifrans.ecommerce.domains.dtos.CommonErroDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoControllerHandler {


    public static void handler(HttpServletRequest request, HttpServletResponse response, Exception exception, HttpStatus status) throws IOException {
        log.error(exception.getMessage());
        var commonErroDto = new CommonErroDto(Objects.requireNonNull(exception.getMessage()), request.getRequestURI());
        summary(List.of(commonErroDto), response, status);
    }

    private static void summary(List<CommonErroDto> commonErroDtoList, HttpServletResponse response, HttpStatus status) throws IOException {
        var json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(commonErroDtoList);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().append(json);
    }
}
