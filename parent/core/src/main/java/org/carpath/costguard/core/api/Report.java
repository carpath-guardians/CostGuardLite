package org.carpath.costguard.core.api;

public interface Report {
    void append(Tenant tenant);

    void append(ReportItem item);
}
