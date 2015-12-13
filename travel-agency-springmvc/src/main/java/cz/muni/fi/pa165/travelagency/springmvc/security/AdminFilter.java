package cz.muni.fi.pa165.travelagency.springmvc.security;

import cz.muni.fi.pa165.travelagency.dto.UserDTO;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;

@WebFilter( urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AdminFilter.class);

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        log.error("protect filter");
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        HttpSession session = request.getSession(true);
        UserDTO user = (UserDTO) session.getAttribute("authUser");
        if (user == null) {
            response401(response);
            return;
        }
        UserFacade userFacade = WebApplicationContextUtils.getWebApplicationContext(r.getServletContext()).getBean(UserFacade.class);
        if (!userFacade.isUserAdmin(user)) {
            response401(response);
            return;
        }
        chain.doFilter(request, response);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.sendRedirect("/auth/login");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
