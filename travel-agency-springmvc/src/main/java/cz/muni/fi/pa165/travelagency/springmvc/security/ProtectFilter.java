package cz.muni.fi.pa165.travelagency.springmvc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;

@WebFilter( urlPatterns = {"/shopping/*"})
public class ProtectFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(ProtectFilter.class);

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        log.error("protect filter");
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;
        
        HttpSession session = request.getSession(true);
        if (session.getAttribute("authUser") == null) {
            response401(response);
            return;
        }
        chain.doFilter(request, response);
    }

    private void response401(HttpServletResponse res) throws IOException {
        res.sendRedirect("/auth/login");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
