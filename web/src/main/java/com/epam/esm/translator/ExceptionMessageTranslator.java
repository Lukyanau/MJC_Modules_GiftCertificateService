package com.epam.esm.translator;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class for exception message translation
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class ExceptionMessageTranslator {

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    /**
     * Method that translate exception message
     * @param messageCode is exception code
     * @return translated message
     */
    public String translateToLocale(String messageCode) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        return resourceBundleMessageSource.getMessage(messageCode, null, currentLocale);
    }
}
