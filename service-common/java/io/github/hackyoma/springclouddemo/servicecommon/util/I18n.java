package io.github.hackyoma.springclouddemo.servicecommon.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * I18n
 *
 * @author hackyo
 * @version 2022/1/25
 */
@Component
public class I18n {

    private static MessageSource messageSource;

    @Autowired
    public I18n(MessageSource messageSource) {
        I18n.messageSource = messageSource;
    }

    public static String get(String message) {
        return I18n.get(message, null);
    }

    public static String get(String message, Object[] args) {
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }

}
