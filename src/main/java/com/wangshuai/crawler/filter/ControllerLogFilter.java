package com.wangshuai.crawler.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xin.allonsy.utils.LogTools;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class ControllerLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        LogTools.addMarker();
        String path = ((HttpServletRequest) request).getServletPath();
        try {
            chain.doFilter(request, response);
        } finally {
            log.info("rt={}ms path={}", (System.currentTimeMillis() - start), path);
            LogTools.removeMarker();
        }
    }

    @Override
    public void destroy() {

    }

}
