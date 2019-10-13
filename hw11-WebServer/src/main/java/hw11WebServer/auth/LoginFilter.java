package hw11WebServer.auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    private ServletContext context;
    private final UserLoginService userService;

    public LoginFilter(UserLoginService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        if (session == null){
            String name = request.getParameter("name");
            String password = request.getParameter("password");

            if (userService.authenticate(name, password)) {
                session = request.getSession();
                session.setMaxInactiveInterval(600);
                session.setAttribute("user", name);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.setStatus(403);
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
