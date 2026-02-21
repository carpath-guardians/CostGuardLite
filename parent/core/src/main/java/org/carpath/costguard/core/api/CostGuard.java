package org.carpath.costguard.core.api;

import java.util.List;

public class CostGuard<T extends Tenant> {
    private final T tenant;
    private final List<ChecklistItem<T>> checklistItems;

    public CostGuard(T tenant, List<ChecklistItem<T>> checklistItems) {
        this.tenant = tenant;
        this.checklistItems = checklistItems;
    }

    public void patrol(Report report) {
        report.append(tenant);
        for (ChecklistItem<T> checklistItem : checklistItems) {
            checklistItem.check(tenant).forEach(report::append);
        }
    }
}
