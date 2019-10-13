package hw11WebServer.usersadmin;

import hw11WebServer.TemplateProcessor;
import hw11WebServer.api.model.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UsersAdminServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private final TemplateProcessor templateProcessor;

    private static final int EXPIRE_INTERVAL = 20; // seconds
    private final UsersAdminService userService;

    public UsersAdminServlet(UsersAdminService userService, TemplateProcessor templateProcessor) {
        this.userService = userService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String,String> parameterValues = new HashMap<>();
        parameterValues.put("name", request.getParameter("name"));
        parameterValues.put("password", request.getParameter("password"));
        parameterValues.put("age", request.getParameter("age"));
        parameterValues.put("address", request.getParameter("address"));
        parameterValues.put("phones", request.getParameter("phones"));

        long id = userService.addUser(parameterValues);

    }

    @Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String methodName = request.getParameter("_method");
        if (methodName.equals("put")){
            doPut(servletRequest, servletResponse);
        }
        listUsers(servletRequest, servletResponse);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        List<List> users = userService.getUsers();
        pageVariables.put("users", users);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
