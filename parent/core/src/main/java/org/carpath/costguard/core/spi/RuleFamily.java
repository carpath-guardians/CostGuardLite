package org.carpath.costguard.core.spi;

import org.carpath.costguard.core.api.ChecklistItem;
import org.carpath.costguard.core.api.Tenant;

import java.util.Map;

public class RuleFamily<T extends Tenant, CI extends ChecklistItem<T>> {
    private final String name;
    private final Map<String, CI> checklistItems;

    public RuleFamily(String name, Class<CI> checklistItemClass) {
        this.name = name;
        checklistItems = SpiUtils.loadRegisteredServices(checklistItemClass);
    }

    public String getName() {
        return name;
    }

    public CI getChecklistItem(String id) {
        return checklistItems.get(id);
    }

    public Map<String, CI> getChecklistItems() {
        return checklistItems;
    }
}
