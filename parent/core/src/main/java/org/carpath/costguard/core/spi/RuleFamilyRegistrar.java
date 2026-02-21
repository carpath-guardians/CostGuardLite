package org.carpath.costguard.core.spi;

import org.carpath.costguard.core.api.ChecklistItem;
import org.carpath.costguard.core.api.Tenant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class RuleFamilyRegistrar {
    private static final Map<String, RuleFamily<Tenant, ChecklistItem<Tenant>>> FAMILIES;

    static {
        ServiceLoader<RuleFamily> familyLoader = ServiceLoader.load(RuleFamily.class);
        familyLoader.reload();
        Map<String, RuleFamily<Tenant, ChecklistItem<Tenant>>> families = new HashMap<>();
        familyLoader.forEach(family -> families.put(family.getName(), family));
        FAMILIES = Collections.unmodifiableMap(families);
    }

    public static Map<String, RuleFamily<Tenant, ChecklistItem<Tenant>>> getRuleFamilies() {
        return FAMILIES;
    }
}
