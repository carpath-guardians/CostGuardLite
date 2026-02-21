package org.carpath.costguard.web;

import org.carpath.costguard.core.api.Report;
import org.carpath.costguard.core.api.ReportItem;
import org.carpath.costguard.core.api.Tenant;

import java.util.ArrayList;
import java.util.List;

public class JsonReportVisitor implements Report {
    private List<JsonReport> jsonReports = new ArrayList<>();
    private JsonReport cursor;

    public List<JsonReport> getJsonReports() {
        return jsonReports;
    }

    @Override
    public void append(Tenant tenant) {
        cursor = new JsonReport();
        cursor.setTenant(tenant.getReportName());
        jsonReports.add(cursor);
    }

    @Override
    public void append(ReportItem item) {
        JsonReport.JsonReportItem jsonReportItem = new JsonReport.JsonReportItem();
        jsonReportItem.setDescription(item.getDescription());
        jsonReportItem.setThresholdDelta(item.getThresholdDelta());
        cursor.addItem(jsonReportItem);
    }
}
