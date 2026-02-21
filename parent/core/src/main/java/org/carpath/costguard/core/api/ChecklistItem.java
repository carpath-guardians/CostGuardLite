package org.carpath.costguard.core.api;

import java.util.List;

public interface ChecklistItem<T extends Tenant> {
    List<ReportItem> check(T tenant);
}
