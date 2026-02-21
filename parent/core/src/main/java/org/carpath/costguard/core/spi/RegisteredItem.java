package org.carpath.costguard.core.spi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RegisteredItem {
    String value();
}
