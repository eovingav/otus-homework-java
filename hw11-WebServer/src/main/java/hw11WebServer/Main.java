package hw11WebServer;



import hw11WebServer.api.model.Role;
import hw11WebServer.auth.AuthorizationFilter;
import hw11WebServer.auth.LoginFilter;
import hw11WebServer.hibernate.HibernateExecutor;
import hw11WebServer.hibernate.HibernateUtils;
import hw11WebServer.auth.UserLoginService;
import hw11WebServer.api.model.AddressDataSet;
import hw11WebServer.api.model.PhoneDataSet;
import hw11WebServer.api.model.User;
import hw11WebServer.sessionmanager.SessionManagerHibernate;
import hw11WebServer.usersadmin.UsersAdminService;
import hw11WebServer.usersadmin.UsersAdminServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/*
Стартовая страница - index.html
Имя пользователя - ИвановИИ
Пароль - 123987456
 */

public class Main {

    private final static int PORT = 8080;
    private final static String STATIC = "/static";
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                AddressDataSet.class, PhoneDataSet.class, Role.class, User.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        HibernateExecutor hibernateExecutor = new HibernateExecutor(sessionManager);
        initializeData(hibernateExecutor);

        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(STATIC);
        resourceHandler.setBaseResource(resource);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        UsersAdminService userService = new UsersAdminService(hibernateExecutor);
        TemplateProcessor templateProcessor = new TemplateProcessor();
        context.addServlet(new ServletHolder(new UsersAdminServlet(userService, templateProcessor)), "/users");

        UserLoginService loginService = new UserLoginService(hibernateExecutor);
        context.addFilter(new FilterHolder(new LoginFilter(loginService)), "/users", null);
        context.addFilter(new FilterHolder(new AuthorizationFilter(loginService)), "/users", null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();

    }

    private static void initializeData(HibernateExecutor hibernateExecutor){

        Role roleAdmin = new Role(0, "admin");
        List<Role> admins = new ArrayList<>();
        admins.add(roleAdmin);

        Role roleUser = new Role(0, "user");
        List<Role> users = new ArrayList<>();
        users.add(roleUser);

        AddressDataSet address = new AddressDataSet(0, "Ленина");
        User user = new User(0, "ИвановИИ", 25, address);
        List<PhoneDataSet> phones = new ArrayList<>();
        phones.add(new PhoneDataSet(0, "+74012359925", user));
        phones.add(new PhoneDataSet(0, "+79216123456", user));
        user.setPhones(phones);
        user.setPassword("123987456");
        user.setRoles(admins);
        long userID = hibernateExecutor.saveObject(user);

        AddressDataSet address2 = new AddressDataSet(0,"Кирова");
        User user2 = new User(0, "ПетровАН", 32, address2);
        List<PhoneDataSet> phones2 = new ArrayList<>();
        phones2.add(new PhoneDataSet(0, "+74012111222", user2));
        phones2.add(new PhoneDataSet(0, "+79219874563", user2));
        user.setPassword("159753");
        user2.setPhones(phones2);
        user2.setRoles(users);
        userID = hibernateExecutor.saveObject(user2);

    }

}
