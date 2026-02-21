package org.carpath.costguard.core.api;

public class ReportItem {
    private final String description;
    private final int thresholdDelta;

    public ReportItem(String description, int thresholdDelta) {
        this.description = description;
        this.thresholdDelta = thresholdDelta;
    }

    public String getDescription() {
        return description;
    }

    public int getThresholdDelta() {
        return thresholdDelta;
    }
}
