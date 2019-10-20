package hw13DI;

import hibernate.HibernateUtils;
import hw13DI.api.model.AddressDataSet;
import hw13DI.api.model.PhoneDataSet;
import hw13DI.api.model.Role;
import hw13DI.api.model.User;
import hw13DI.hibernate.HibernateExecutor;
import hw13DI.sessionmanager.SessionManagerHibernate;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@EnableWebMvc
@Configuration
@ComponentScan
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    ApplicationContext applicationContext;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        var viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }


    @Bean
    public SessionFactory sessionFactory(){
        return HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                AddressDataSet.class, PhoneDataSet.class, Role.class, User.class);
    }

    @Bean
    public SessionManagerHibernate sessionManager(){
        return new SessionManagerHibernate(sessionFactory());
    }

    @Bean
    public HibernateExecutor hibernateExecutor() {
        return new HibernateExecutor(sessionManager());
    }

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations("/WEB-INF/static/");
    }
}
