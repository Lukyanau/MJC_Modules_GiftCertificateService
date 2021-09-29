package com.epam.esm.configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web configuration with message source bean, that translate exception messages.
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
public class WebConfig implements WebMvcConfigurer {

  /**
   * Bean for searching exception message description
   *
   * @return ResourceBundle
   */
  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasename("language.language");
    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
    resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    return resourceBundleMessageSource;
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.stream()
        .filter(converter -> converter instanceof AbstractJackson2HttpMessageConverter)
        .map(converter -> (AbstractJackson2HttpMessageConverter) converter)
        .forEach(
            jacksonConverter ->
                jacksonConverter
                    .getObjectMapper()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
  }
}
