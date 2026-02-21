package org.carpath.costguard.core.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

class SpiUtils {
    static <T> Map<String, T> loadRegisteredServices(Class<T> serviceClass) {
        Map<String, T> services = new HashMap<>();
        ServiceLoader<T> loader = ServiceLoader.load(serviceClass);
        for (T service : loader) {
            Optional.ofNullable(service.getClass().getAnnotation(RegisteredItem.class))
                    .ifPresent(registeredItem -> services.put(registeredItem.value(), service));
        }
        return Collections.unmodifiableMap(services);
    }
}
