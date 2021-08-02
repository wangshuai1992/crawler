package com.wangshuai.crawler.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.ContentCachingRequestWrapper;
import xin.allonsy.common.vo.BaseJson;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 全局controller异常处理
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseJson handler404(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL().toString() + " not found.", e);
        return new BaseJson("not found.");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseJson handlerError(HttpServletRequest httpServletRequest, Exception e) throws Exception {
        String requestPath = httpServletRequest.getRequestURL().toString();
        Map<String, String[]> paramMap = httpServletRequest.getParameterMap();
        String requestBody =
            StringUtils.toEncodedString(((ContentCachingRequestWrapper)httpServletRequest).getContentAsByteArray(),
                Charset.forName(httpServletRequest.getCharacterEncoding()));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(requestPath).append("    param:{");

        paramMap
            .forEach((key, value) -> stringBuilder.append(key).append("=[").append(JSON.toJSON(value)).append("]&"));
        if (stringBuilder.charAt(stringBuilder.length() - 1) == '&') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("}").append("    requestBody:[").append(requestBody).append("]");
        log.error(stringBuilder.toString(), e);
        return new BaseJson(e.getMessage());
    }

    @ExceptionHandler(value = InvalidOperationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseJson handlerInvalidOperationError(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL().toString() + " bad request.", e);
        return new BaseJson("bad request. " + e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseJson handlerUnauthorizedError(HttpServletRequest req, Exception e) throws Exception {
        log.error(req.getRequestURL().toString() + " unauthorized.", e);
        return new BaseJson("unauthorized. " + e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseJson handlerBadRequestError(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error(req.getRequestURL().toString() + " bad request.", e);
        return new BaseJson("bad request. " + e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public BaseJson handlerJsonMappingError(HttpServletRequest request, Exception e) {
        log.error("HttpMessageNotReadableException occur.", e);
        return new BaseJson("request not readable. " + e.getMessage());
    }

}
