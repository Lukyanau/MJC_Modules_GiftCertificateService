//package com.epam.esm.configuration;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//
//public class MyWebApplicationInitializerConfig implements WebApplicationInitializer {
//    @Override
//    public void onStartup(javax.servlet.ServletContext servletContext){
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.register(WebConfig.class);
//        applicationContext.setServletContext(servletContext);
//
//    }
//}
