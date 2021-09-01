package com.epam.esm.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext){
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class);
        applicationContext.setServletContext(servletContext);
        servletContext.addListener(new ContextLoaderListener(applicationContext));
        servletContext.setInitParameter("spring.profiles.active", "prod");
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("DispatcherServlet", new DispatcherServlet(applicationContext));
        dispatcher.addMapping("/");
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound","true");
        dispatcher.setLoadOnStartup(1);
    }
}
