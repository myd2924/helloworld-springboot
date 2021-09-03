package com.myd.helloworld.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/3 14:50
 * @Description: 过滤器1号
 */
public class Filter1 implements Filter {

    private static final Logger log = LoggerFactory.getLogger(Filter1.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Filter1 init(),"+ LocalDateTime.now());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter1."+" before");
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        long end = System.currentTimeMillis();
        long time = end-start;
        final String path = ((HttpServletRequest) servletRequest).getServletPath();
        log.info("doFilter1."+time+","+path + "after");
    }

    @Override
    public void destroy() {
        log.info("Filter1.destroy()=================");
    }
}
