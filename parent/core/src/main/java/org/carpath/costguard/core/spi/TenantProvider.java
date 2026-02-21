package org.carpath.costguard.core.spi;

import org.carpath.costguard.core.api.Tenant;

import java.util.Map;

public interface TenantProvider<T extends Tenant> {
    T getTenant(Map<String, String> parameters);
}
