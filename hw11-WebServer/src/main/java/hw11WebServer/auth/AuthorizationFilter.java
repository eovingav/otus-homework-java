package hw11WebServer.auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    private ServletContext context;
    private final UserLoginService userService;

    public AuthorizationFilter(UserLoginService userService) {
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
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        if (session == null) {
            res.setStatus(403);
        } else {
            String userName = (String) session.getAttribute("user");
            String adminRoleName = "admin";
            if (userService.userHasRole(userName, adminRoleName)){
                filterChain.doFilter(servletRequest, servletResponse);
            }else {

            }

        }

    }

    @Override
    public void destroy() {

    }
}
