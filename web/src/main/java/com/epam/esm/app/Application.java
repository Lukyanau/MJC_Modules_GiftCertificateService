package com.epam.esm.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan("com.epam.esm")
@EnableWebMvc
@EntityScan(basePackages = "com.epam.esm.entity")
public class Application {

  private final DispatcherServlet servlet;

  @Autowired
  public Application(DispatcherServlet servlet) {
    this.servlet = servlet;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner getCommandLineRunner(ApplicationContext applicationContext) {
    servlet.setThrowExceptionIfNoHandlerFound(true);
    return args -> {};
  }
}
