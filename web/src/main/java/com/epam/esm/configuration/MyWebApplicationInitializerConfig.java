package com.epam.esm.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializerConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
//    @Override
//    public void onStartup(ServletContext servletContext){
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.register(WebConfig.class);
//        applicationContext.setServletContext(servletContext);
//        servletContext.addListener(new ContextLoaderListener(applicationContext));
//        servletContext.setInitParameter("spring.profiles.active", "prod");
//        ServletRegistration.Dynamic dispatcher =
//                servletContext.addServlet("DispatcherServlet", new DispatcherServlet(applicationContext));
//        dispatcher.addMapping("/");
//        dispatcher.setLoadOnStartup(1);
//    }
}
